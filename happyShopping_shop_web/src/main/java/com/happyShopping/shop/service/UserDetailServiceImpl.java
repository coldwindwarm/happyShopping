/**
 * FileName: UserDetailServiceImpl
 * Author:   coldwind
 * Date:     2018/8/12 12:15
 * Description: 权限认证
 * History:
 * <author>          <time>          <version>          <desc>
 */
package com.happyShopping.shop.service;

import com.happyShopping.model.Seller;
import com.happyShopping.sellergoods.service.SellerService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;

/**
 * 〈一句话功能简述〉<br> 
 * 〈权限认证〉
 *
 * @author coldwind
 * @create 2018/8/12
 * @since 1.0.0
 */
public class UserDetailServiceImpl implements UserDetailsService {
    /**
     * Locates the user based on the username. In the actual implementation, the search
     * may possibly be case sensitive, or case insensitive depending on how the
     * implementation instance is configured. In this case, the <code>UserDetails</code>
     * object that comes back may have a username that is of a different case than what
     * was actually requested..
     *
     * @param username the username identifying the user whose data is required.
     * @return a fully populated user record (never <code>null</code>)
     * @throws UsernameNotFoundException if the user could not be found or the user has no
     *                                   GrantedAuthority
     */

    //不用注入@refrence的方式注入sellerService
    private SellerService sellerService;

    public SellerService getSellerService() {
        return sellerService;
    }

    public void setSellerService(SellerService sellerService) {
        this.sellerService = sellerService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        ArrayList<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_SELLER"));
//        查询用户名对应的用户,判断其密码是否正确和是否已审核状态,都正确的话就让他通过
        Seller seller = sellerService.findOne(username);
        if (seller != null){
            if (seller.getStatus().equals(Seller.CHECKED)){
                return new User(username,seller.getPassword(),grantedAuthorities);
            }else {
                return null;
            }
        }else {
            return null;
        }
    }
}
