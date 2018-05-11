package com.maple.cse308.service;

import com.maple.cse308.entity.*;
import com.maple.cse308.enums.Visibility;
import com.maple.cse308.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.maple.cse308.enums.Visibility.FULL;

@Service
public class UserServiceImpl implements UserService {


    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private TvShowRepository tvShowRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CriticRepository criticRepository;
    @Autowired
    private MovieReviewUserRepository movieReviewUserRepository;
    @Autowired
    private MovieReviewCriticRepository movieReviewCriticRepository;
    @Autowired
    private EmailServiceImpl emailService;

   /* @Autowired
    private TvReviewUserRepository tvReviewUserRepository;

    @Autowired
    private TvReviewCriticRepository tvReviewCriticRepository;
    */

    @Override
    public String changePhoto(String photo) {

        User user = getCurrentUser();
        user.setPhoto(photo);
        userRepository.save(user);
        updateUser();
        return photo;
    }

    @Override
    public String changeCountry(String country) throws Exception {
        Pattern p = Pattern.compile("^[ A-Za-z]*$");
        Matcher m = p.matcher(country);

        if (m.matches()) {
            User user = getCurrentUser();
            user.setCountry(country);
            userRepository.save(user);
            updateUser();
            return user.getCountry();
        } else {
            throw new Exception("Error: Illegal Characters");
        }
    }

    //password encoder added
    @Override
    public String changePassword(String oldPass, String newPass) throws Exception {
        User user = getCurrentUser();
        if (passwordEncoder.matches(oldPass, user.getPassword())) {
            user.setPassword(passwordEncoder.encode(newPass));
            userRepository.save(user);
            updateUser();
            return "Success";
        } else {
            throw new Exception("Error: Incorrect Password");
        }
    }

    @Override
    public String changeHomeTown(String homeTown) throws Exception {
        Pattern p = Pattern.compile("^[ A-Za-z]*$");
        Matcher m = p.matcher(homeTown);

        if (m.matches()) {
            User user = getCurrentUser();
            user.setHometown(homeTown);
            userRepository.save(user);
            updateUser();
            return user.getHometown();
        } else {
            throw new Exception("Error: Illegal Characters");
        }
    }

    @Override
    public String changeBio(String bio) throws Exception {

        int maxLength = 512;
        if (bio.length() <= maxLength) {
            User user = getCurrentUser();
            user.setBio(bio);
            userRepository.save(user);
            updateUser();
            return user.getBio();
        } else {
            throw new Exception("Error: You have exceeded the maximum allowed length");
        }
    }

    @Override
    public String changeVisibility(Visibility visibility) {

        User user = getCurrentUser();
        user.setVisibility(visibility);
        userRepository.save(user);
        updateUser();
        return visibility.toString();
    }

    //allows user to delete their own account
    @Override
    public void deleteUser() {
        User user = getCurrentUser();

        if (confirmCurrentRole("ROLE_USER")) {
            Set<MovieReviewUser> movieReviewUsers = movieReviewUserRepository.findAllByUserId(user.getUserId());
            for (MovieReviewUser movieReviewUser : movieReviewUsers) {
                movieReviewUserRepository.delete(movieReviewUser);
            }
      /*  Set<TvReviewUser> tvReviewUsers = tvReviewUserRepository.findAllByUserId(user.getUserId());
        for (TvReviewUser tvReviewUser : tvReviewUsers) {
            tvReviewUserRepository.delete(tvReviewUser);
        }*/
        } else if (confirmCurrentRole("ROLE_CRITIC")) {
            Critic critic = criticRepository.findByUser(user);
            Set<MovieReviewCritic> movieReviewCritics = movieReviewCriticRepository.findAllByCriticId(critic.getCriticId());
            for (MovieReviewCritic movieReviewCritic : movieReviewCritics) {
                movieReviewCriticRepository.delete(movieReviewCritic);
            }
      /*  Set<TvReviewCritic> tvReviewCritics = tvReviewCriticRepository.findAllByCriticId(critic.getCriticId());
        for (TvReviewCritic tvReviewCritic : tvReviewCritics) {
            tvReviewCriticRepository.delete(tvReviewUser);
        }*/
        }
        userRepository.delete(user);
    }

    //allows administrator to delete a user account
    public void deleteUser(String username) {

        User user = findByUsername(username);
        if (user.getRoles().contains("ROLE_USER")) {
            Set<MovieReviewUser> movieReviewUsers = movieReviewUserRepository.findAllByUserId(user.getUserId());
            for (MovieReviewUser movieReviewUser : movieReviewUsers) {
                movieReviewUserRepository.delete(movieReviewUser);
            }
            /*  Set<TvReviewUser> tvReviewUsers = tvReviewUserRepository.findAllByUserId(user.getUserId());
                for (TvReviewUser tvReviewUser : tvReviewUsers) {
                    tvReviewUserRepository.delete(tvReviewUser);
                }*/
        } else if (user.getRoles().contains("ROLE_CRITIC")) {
            Critic critic = criticRepository.findByUser(user);
            Set<MovieReviewCritic> movieReviewCritics = movieReviewCriticRepository.findAllByCriticId(critic.getCriticId());
            for (MovieReviewCritic movieReviewCritic : movieReviewCritics) {
                movieReviewCriticRepository.delete(movieReviewCritic);
            }
                /* Set<TvReviewCritic> tvReviewCritics = tvReviewCriticRepository.findAllByCriticId(critic.getCriticId());
                for (TvReviewCritic tvReviewCritic : tvReviewCritics) {
                tvReviewCriticRepository.delete(tvReviewUser);
                }*/
        }
            userRepository.delete(user);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    //get current user from security context singleton
    @Override
    public User getCurrentUser() {
        UserDetailsImpl userDetailsImpl = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userDetailsImpl.getUser();
    }

    //confirm current authorization level of user
    @Override
    public boolean confirmCurrentRole(String role) {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        for (GrantedAuthority auth : authentication.getAuthorities()) {
            if ((role).equals(auth.getAuthority()))
                return true;
        }
        return false;
    }

    @Override
    public void updateUser() {
        User user = findByUsername(getCurrentUser().getUsername());
        UserDetailsImpl userDetails = new UserDetailsImpl(user);
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        return new UserDetailsImpl(user);
    }

    public void registerUser(User user) throws Exception {

        if (userRepository.existsByUsername(user.getUsername())) {
            throw new Exception("Error: username already in use");
        } else if (userRepository.existsByEmail(user.getEmail())) {
            throw new Exception("Error: email is already in use");
        }
        user.setVisibility(FULL);
        user.setRoles(roleRepository.findByRole("ROLE_USER"));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    //No criteria for top critic
    //Criteria to reject application?
    //How to allow user to add multiple group?
    //How to allow user to add multiple publications?
    //Setting this up for administrator to approve or not approve?
    /*public void criticApplication(String website, Set<Groups> groups, Set<Publications> publications) throws Exception {

        User user = getCurrentUser();
        user.setRoles(roleRepository.findByRole("ROLE_CRITIC"));
        updateUser();
        Critic critic = new Critic();
        critic.setUser(user);
        critic.setFirstName(user.getFirstName());
        critic.setMiddleName(user.getMiddleName());
        critic.setLastName(user.getLastName());
        critic.setPhoto(user.getPhoto());
        critic.setWebsite(website);
        critic.setGroups(groups);
        critic.setPublishers(publications);
        criticRepository.save(critic);
    }*/

    public void criticApplication(String website, String groups, String publications) throws Exception {

        User user = getCurrentUser();
        user.setRoles(roleRepository.findByRole("ROLE_CRITIC"));
        updateUser();
        Critic critic = new Critic();
        critic.setUser(user);
        critic.setFirstName(user.getFirstName());
        critic.setMiddleName(user.getMiddleName());
        critic.setLastName(user.getLastName());
        critic.setPhoto(user.getPhoto());
        critic.setWebsite(website);

        List<String> groupList = new ArrayList<String>(Arrays.asList(groups.split("\\s*,\\s*")));
        List<String> publicationList = new ArrayList<String>(Arrays.asList(publications.split("\\s*,\\s*")));

        Set<Publications> publisher = new HashSet<>();
        Set<Groups> group = new HashSet<>();

        for (int i = 0; i < publicationList.indexOf(i); i++) {
            Publications pub = new Publications();
            pub.setPublication(publicationList.get(i));
            publisher.add(pub);
        }

        for (int i = 0; i < groupList.indexOf(i); i++) {
            Groups grp = new Groups();
            grp.setGroupName(groupList.get(i));
            group.add(grp);
        }
        critic.setGroups(group);
        critic.setPublishers(publisher);
        criticRepository.save(critic);
    }

    @Override
    public void addEmployee(User user) throws Exception {

        if (confirmCurrentRole("ADMIN")) {
            user.setRoles(roleRepository.findByRole("ADMIN"));
            userRepository.save(user);
        } else {
            throw new Exception("Error: Currently logged in user is not an administrator and can not add employee");
        }

    }

    @Override
    public void removeEmployee(User user) throws Exception {

        if (confirmCurrentRole("ADMIN")) {
            user.setRoles(roleRepository.findByRole("USER"));
            userRepository.save(user);
        } else {
            throw new Exception("Error: Currently logged in user is not an administrator and can not demote an employee");
        }

    }

    @Override
    public void banUser(User user) throws Exception {

        if (confirmCurrentRole("ADMIN")) {
            user.setRoles(roleRepository.findByRole("BANNED"));
            userRepository.save(user);
        } else {
            throw new Exception("Error: Currently logged in user is not an administrator and can not ban user");
        }

    }

    @Override
    public void suspendUser(User user, Calendar calendar) throws Exception {

        if (confirmCurrentRole("ADMIN")) {
            user.setRoles(roleRepository.findByRole("SUSPENDED"));
            user.setSuspendDate(calendar);
            userRepository.save(user);
        } else {
            throw new Exception("Error: Currently logged in user is not an administrator and can not ban someone");
        }

    }

    @Override
    public void addToBlocklist(String username) throws Exception {

        User user = getCurrentUser();
        User blockedUser = userRepository.findByUsername(username);
        if (user.getBlocklist().contains(blockedUser)) {
            throw new Exception("Error: The user is already on your blocked list");
        } else {
            user.getBlocklist().add(blockedUser);
            userRepository.save(user);
        }

    }

    @Override
    public void removeFromBlocklist(String username) throws Exception {

        User user = getCurrentUser();
        User blockedUser = userRepository.findByUsername(username);
        if (!user.getBlocklist().contains(blockedUser)) {
            throw new Exception("Error: The user is not on your blocked list");
        } else {
            user.getBlocklist().remove(blockedUser);
            userRepository.save(user);
        }

    }

    @Override
    public Set<Movie> getWantToSeeList() {
        return getCurrentUser().getWatchList();
    }

    @Override
    public void addToWantToSeeList(Movie movie) throws Exception {
        User user = getCurrentUser();
        if(!user.getDontWatchList().contains(movie)) {
            user.getWatchList().add(movie);
            userRepository.save(user);
            updateUser();
        }
        else{
            throw new Exception("Error: Cannot add a movie that is already on your ignore list");
        }
    }

    @Override
    public void removeFromWantToSeeList(Movie movie) throws Exception {
        User user = getCurrentUser();
        Movie m = new Movie();
        for (Movie mv : user.getWatchList()){
            if(mv.getMovieId() == movie.getMovieId()){
                m = mv;
                break;
            }
        }
        user.getWatchList().remove(m);
        userRepository.save(user);
        updateUser();
        /*
        if(user.getWatchList().contains(movie)) {
            user.getWatchList().remove(movie);
            userRepository.save(user);
            updateUser();
        }else{
            throw new Exception("Error: Movie is not on your watch list");
        }
        */
    }

    @Override
    public Set<Movie> getDontWantToSeeList() {
        return getCurrentUser().getDontWatchList();
    }

    @Override
    public void addToDontWantToSeeList(Movie movie) throws Exception{
        User user = getCurrentUser();
        if(!user.getWatchList().contains(movie)) {
            user.getDontWatchList().add(movie);
            userRepository.save(user);
            updateUser();
        }
        else{
            throw new Exception("Error: Cannot add a movie that is already on your watch list");
        }
    }

    @Override
    public void removeFromDontWantToSeeList(Movie movie) throws Exception{
        User user = getCurrentUser();
        Movie m = new Movie();
        for (Movie mv : user.getDontWatchList()){
            if(mv.getMovieId() == movie.getMovieId()){
                m = mv;
                break;
            }
        }
        user.getDontWatchList().remove(m);
        userRepository.save(user);
        updateUser();

        /*
        if(user.getDontWatchList().contains(movie)) {
            user.getDontWatchList().remove(movie);
            userRepository.save(user);
            updateUser();
        }else{
            throw new Exception("Error: Movie is not on your ignore list");
        }
        */
    }

    @Override
    public void addMovie(Movie movie) throws Exception {
        if(movieRepository.existsByTitleAndReleaseDate(movie.getTitle(), movie.getReleaseDate())){
            throw new Exception("Error: This movie already exists in the database");
        }else if(!confirmCurrentRole("ADMIN")){
            throw new Exception("Error: You are not an administrator");
        }else{
            movieRepository.save(movie);
        }
    }

    @Override
    public void editMovie(Movie movie) throws Exception {
        if(confirmCurrentRole("ADMIN")) {
            movieRepository.save(movie);
        }else{
            throw new Exception("Error: You are not an administrator");
        }
    }

    @Override
    public void addTvShow(TvShow tvShow) throws Exception {
        if(tvShowRepository.existsByTitleAndPremierDate(tvShow.getTitle(),tvShow.getPremierDate())){
            throw new Exception("Error: This TV show already exists in the database.");
        }else if(!confirmCurrentRole("ADMIN")){
            throw new Exception("Error: You are not an administrator");
        }else{
            tvShowRepository.save(tvShow);
        }
    }

    @Override
    public void editTvShow(TvShow tvShow) throws Exception {
        if (confirmCurrentRole("ADMIN")) {
            tvShowRepository.save(tvShow);
        } else {
            throw new Exception("Error: You are not an administrator");
        }
    }

     /*
    public void addToBlocklist(String username) throws Exception {

        User user = getCurrentUser();
        User blockedUser = userRepository.findByUsername(username);
        BlockIdentity blockId = new BlockIdentity(user.getUserId(), blockedUser.getUserId());
        if (blockRepository.existsByBlockIdentity(blockId)) {
            throw new Exception("Error: The user is already on your blocked list");
        } else {
            Block block = new Block();
            block.setBlockIdentity(blockId);
            blockRepository.save(block);
        }

    }

    public void removeFromBlocklist(String username) throws Exception {

        User user = getCurrentUser();
        User blockedUser = userRepository.findByUsername(username);
        BlockIdentity blockId = new BlockIdentity(user.getUserId(), blockedUser.getUserId());
        if (!blockRepository.existsByBlockIdentity(blockId)) {
            throw new Exception("Error: The user is not on your blocked list");
        } else {
            Block block = blockRepository.findByBlockIdentity(blockId);
            blockRepository.delete(block);
        }

    }

    public List<Block> getBlockList(Integer userId){
        return blockRepository.findByBlockingIdBlockerId(userId);
    }
    */

     public void resetPassword(String email, HttpServletRequest request) throws Exception{
         User user = userRepository.findByEmail(email);
         if(user == null){
            throw new Exception("Error: This user does not exist");
         }else{
             String token = UUID.randomUUID().toString();
             user.setResetToken(token);
             userRepository.save(user);

             String resetUrl = request.getScheme() + "://" + request.getServerName();
             emailService.sendSimpleMessage(user.getEmail(),"Rotten Tomatoes: Passsword Reset Request","To reset your password, click the link below:\n" + resetUrl
                     + "/reset?token=" + user.getResetToken());
         }








     }

}