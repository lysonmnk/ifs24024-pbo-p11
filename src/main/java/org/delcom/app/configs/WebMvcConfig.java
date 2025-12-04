package org.delcom.app.configs;

import org.delcom.app.interceptors.AuthInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Autowired
    private AuthInterceptor authInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authInterceptor)
                // 1. UBAH CAKUPAN: Terapkan ke SEMUA endpoint (Web & API)
                // Kalau cuma "/api/**", nanti Dashboard ("/") tidak akan dicek keamanannya.
                .addPathPatterns("/**") 
                
                // 2. KODE LAMA KAMU (DIPERTAHANKAN)
                .excludePathPatterns("/api/auth/**") 
                .excludePathPatterns("/api/public/**")

                // 3. TAMBAHAN WAJIB UNTUK WEB (Supaya Login & Tampilan tidak rusak)
                .excludePathPatterns("/auth/**")   // Halaman Login & Register Web
                .excludePathPatterns("/css/**")    // File CSS (Desain)
                .excludePathPatterns("/js/**")     // File JavaScript
                .excludePathPatterns("/images/**") // Gambar
                .excludePathPatterns("/error");    // Halaman Error Default
    }
}