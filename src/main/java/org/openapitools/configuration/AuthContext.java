package org.openapitools.configuration;

public class AuthContext {

  private static final ThreadLocal<Long> _userId = new ThreadLocal<>();
  private static final ThreadLocal<Integer> _userVersion = new ThreadLocal<>();

  public static void setUserId(Long userId) {
    _userId.set(userId);
  }

  public static void setUserVersion(Integer userVersion) {
    _userVersion.set(userVersion);
  }

  public static Long getUserId() {
    return _userId.get();
  }

  public static Integer getUserVersion() {
    return _userVersion.get();
  }

  public static void clear() {
    _userId.remove();
    _userVersion.remove();
  }
}