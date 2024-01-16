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
     * Get the password of the user
     *
     * @return String with the password
     * @throws Exception Exception
     */
    String getPassword() throws Exception;

    /**
     *
     * Get the nif of the user
     *
     * @return nif of the user
     *
     */
    String getnif();

    /**
     *
     * Set the nif of the user
     *
     * @param nif nif of the user
     *
     */
    void setnif(String nif);
}
