package com.beijiake.authorserver.config;


import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.util.Assert;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


public class JdbcUserDetailsService implements UserDetailsService {

    private String selectCredentialFromName = "select * from credentials where name=?";
    private String selectAuthoritiesByAccountID = "select b.authority from credentials_authorities a left join authority b on a.authorities_id = b.id where a.credentials_id=?";



    private  final JdbcTemplate jdbcTemplate;

    JdbcUserDetailsService(DataSource dataSource) {
        Assert.notNull(dataSource, "DataSource required");
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {


        try {


            User user = jdbcTemplate.queryForObject(this.selectCredentialFromName, (rs, rowNum) -> {

                int id = rs.getInt("id");
                String userName = rs.getString("name");
                String password = rs.getString("password");
                boolean enabled = rs.getBoolean("enabled");

                List<GrantedAuthority> authorites = jdbcTemplate.query(this.selectAuthoritiesByAccountID, new JdbcUserDetailsService.AuthoritiesRowMapper(), new Object[] {id});
                return new User(userName, password, enabled, true, true, true, authorites);
            }, new Object[]{username});



            return user;
        }
        catch (EmptyResultDataAccessException exception) {
            throw new UsernameNotFoundException("用户或者密码错误");
        }

    }

    private final class AuthoritiesRowMapper implements RowMapper<GrantedAuthority> {
        @Override
        public GrantedAuthority mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new SimpleGrantedAuthority(rs.getString("authority"));
        }
    }
}
