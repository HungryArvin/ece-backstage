package sc.ete.backstage.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * @author ：Arvin
 * @Description:    JWT规则的token工具类，提供生成和解析JWT的方法
 * @name：JwtUtil
 */
@Slf4j
public class JwtUtil {
    public static void main(String[] args) {
        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhcnZpbi11c2VyIiwiaWF0IjoxNjE2NTk1MTExLCJleHAiOjE2MTY2ODE1MTF9.hG_Zypsh8vcoHNShyMfrm7oVIju0kd5viP6qHMn-RRI");

        Claims claims = claimsJws.getBody();
        System.out.println(claims);
        System.out.println(claims.get("id"));
        System.out.println(claims.get("username"));
    }
    //Jwt的有效时间--》一天
    public static final long EXPIRE = 1000 * 60 * 60 * 24;
    //这个是密钥
    public static final String APP_SECRET = "ukc8BDbRigUDaY6pZFfWus2jWIU";

    public static String getJwtToken(String id, String username){
        String JwtToken = Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setHeaderParam("alg", "HS256")
                .setSubject("arvin-user")
                .setIssuedAt(new Date())
                //有效时间设置  当前时间+有效时间
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE))

                //有效载荷部分
                .claim("id", id) //id
                .claim("username", username) //昵称

                //签没哈希部分
                .signWith(SignatureAlgorithm.HS256, APP_SECRET)
                .compact();
        return JwtToken;
    }
    /**
     * 判断token是否存在与有效
     * @param jwtToken
     * @return
     */
    public static boolean checkToken(String jwtToken) {
        if(StringUtils.isEmpty(jwtToken)) return false;

        try {
            //解析
            Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(jwtToken);
        } catch (Exception e) {
            //解析失败
            e.printStackTrace();
            return false;
        }
        //解析成功---》说明已经登录
        return true;
    }
    /**
     * 判断token是否存在与有效 --》request请求头的方式判断
     * @param request
     * @return
     */
    public static boolean checkToken(HttpServletRequest request) {
        try {
            String jwtToken = request.getHeader("X-Token");
            if(StringUtils.isEmpty(jwtToken)) return false;

            Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(jwtToken);

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }
    /**
     * 根据token获取会员id ---》通过请求头获取
     * @param request
     * @return
     */
    public static String getMemberIdByJwtToken(HttpServletRequest request) {
        String jwtToken = request.getHeader("X-Token"); //获取token头
        log.info("获取到的token:{}",jwtToken);
        if(StringUtils.isEmpty(jwtToken)) return "";

        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(jwtToken);

        Claims claims = claimsJws.getBody();

        return (String)claims.get("id"); //返回用户id
    }

    /**
     * 根据token获取username
     * @return
     */
    public static String getUsernameFromToken(String token) {
        if(StringUtils.isEmpty(token)) return "";

        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(token);

        Claims claims = claimsJws.getBody();

        return (String)claims.get("username"); //返回用户id
    }

    public static void removeToken(String token){

    }
}
