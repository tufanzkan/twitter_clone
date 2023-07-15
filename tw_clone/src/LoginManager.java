public class LoginManager {
    public boolean login(String username, String password) {
        // Burada gerçek bir giriş kontrolü yapılabilir
        // Örneğin, hardcoded bir kullanıcı adı ve şifre ile karşılaştırma yapabiliriz
        // Gerçek bir kullanıcı veritabanı entegrasyonu veya başka bir kimlik doğrulama mekanizması da kullanılabilir

        return username.equals("admin") && password.equals("admin");
    }
}
