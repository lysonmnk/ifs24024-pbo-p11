package org.delcom.app.interceptors;

import org.delcom.app.configs.AuthContext;
import org.delcom.app.entities.AuthToken;
import org.delcom.app.entities.User;
import org.delcom.app.services.AuthTokenService;
import org.delcom.app.services.UserService;
import org.delcom.app.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.UUID;

@Component
public class AuthInterceptor implements HandlerInterceptor {

    @Autowired
    protected AuthContext authContext;

    @Autowired
    protected AuthTokenService authTokenService;

    @Autowired
    protected UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        
        // 1. Cek apakah ini halaman yang bebas akses (Login/Register/Gambar)
        if (isPublicEndpoint(request)) {
            return true; // Langsung lolos
        }

        // 2. Cek Token (Biasanya dipakai API)
        String rawAuthToken = request.getHeader("Authorization");
        String token = extractToken(rawAuthToken);

        // =================================================================
        // 🚨 BAGIAN PENTING: JANGAN ERROR KALAU TOKEN KOSONG!
        // =================================================================
        // Kalau token kosong, berarti yang akses adalah BROWSER (Manusia).
        // Kita Return TRUE (Biarkan lewat).
        // Nanti 'HomeView' atau 'SecurityConfig' yang akan menyuruh Login.
        if (token == null || token.isEmpty()) {
            return true; 
        }
        // =================================================================

        // 3. Validasi Token (Jika tokennya ada)
        if (!JwtUtil.validateToken(token, true)) {
            return true; // Token salah? Biarkan lewat sebagai 'Tamu'
        }

        UUID userId = JwtUtil.extractUserId(token);
        if (userId == null) return true;

        AuthToken authToken = authTokenService.findUserToken(userId, token);
        if (authToken == null) return true;

        User authUser = userService.getUserById(authToken.getUserId());
        if (authUser == null) return true;

        // Set User ke Context
        authContext.setAuthUser(authUser);
        return true;
    }

    private String extractToken(String rawAuthToken) {
        if (rawAuthToken != null && rawAuthToken.startsWith("Bearer ")) {
            return rawAuthToken.substring(7);
        }
        return null;
    }

    private boolean isPublicEndpoint(HttpServletRequest request) {
        String path = request.getRequestURI();
        
        // Daftar halaman yang boleh lewat tanpa pemeriksaan
        return path.equals("/") ||            // Dashboard
               path.startsWith("/auth") ||    // Halaman Login & Register
               path.startsWith("/css") ||     // File Desain
               path.startsWith("/js") ||      // File Script
               path.startsWith("/images") ||  // Gambar
               path.startsWith("/error");     // Halaman Error
    }
}