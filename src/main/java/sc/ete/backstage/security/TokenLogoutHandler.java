package sc.ete.backstage.security;


import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import sc.ete.backstage.utils.JwtUtil;
import sc.ete.backstage.utils.R;
import sc.ete.backstage.utils.ResponseUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 * 登出业务逻辑类
 * </p>
 */
public class TokenLogoutHandler implements LogoutHandler {

    private RedisTemplate redisTemplate;

    public TokenLogoutHandler( RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        String token = request.getHeader("X-Token");
        if (token != null) {
            JwtUtil.removeToken(token);

            //清空当前用户缓存中的权限数据
            String userName = JwtUtil.getUsernameFromToken(token);
            redisTemplate.delete(userName);
        }
        ResponseUtil.out(response, R.right());
    }

}