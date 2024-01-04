package com.petcare.petcare.Users;

public interface IUser {
    /**
     *
     * Set the username of the user
     *
     * @param username Username of the user
     *
     */
    void setUsername(String username);

    /**
     *
     * Get the user username
     *
     * @return Username of the user
     *
     */
    String getUsername();

    /**
     *
     * Set the password of the user
     *
     * @param password Password of the user
     *
     */
    void setPassword(String password) throws Exception;

    /**
     *
     * Set the online status of the user
     *
     * @param isOnline Online status of the user
     *
     */
    void setOnline(boolean isOnline);

    /**
     *
     * Get the online status of the user
     *
     * @return True if the user is online, false otherwise
     * @see #isOnline()
     *
     */
    boolean isOnline();

    /**
     *
     * Get the password of the user
     *
     * @return String with the password
     * @throws Exception Exception
     */
    String getPassword() throws Exception;

    /**
     *
     * Check if the password is correct
     *
     * @param password Password to check
     *
     * @return True if the password is correct, false otherwise
     * @throws Exception Exception
     *
     */
    boolean checkPassword(String password) throws Exception;

    /**
     *
     * Get the NIF of the user
     *
     * @return NIF of the user
     *
     */
    public String getNIF();

    /**
     *
     * Set the NIF of the user
     *
     * @param NIF NIF of the user
     *
     */
    public void setNIF(String NIF);
}
