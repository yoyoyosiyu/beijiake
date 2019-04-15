package com.beijiake.console_services.config;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.Set;


public class MyAccessDecisionManager implements AccessDecisionManager {

    private String sqlGetPrivilegesByAuthority = "SELECT a.authority, p.meta, length(meta) as len FROM authority a LEFT JOIN authority_privilege b ON a.id = b.authority_id LEFT JOIN privileges p ON b.privilege_id = p.id WHERE a.authority IN (:authorities) order by len asc, authority asc";

    private final NamedParameterJdbcTemplate jdbcTemplate;

    MyAccessDecisionManager(DataSource dataSource) {
        jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public void decide(Authentication authentication, Object o, Collection<ConfigAttribute> collection) throws AccessDeniedException, InsufficientAuthenticationException {
        if (!(authentication instanceof OAuth2Authentication)) {
            throw new InsufficientAuthenticationException("");
        }

        //OAuth2Authentication oAuth2Authentication = (OAuth2Authentication) authentication;

        //FilterInvocation filterInvocation = (FilterInvocation) o;

        authentication.getAuthorities();



        Set<String> authorities = AuthorityUtils.authorityListToSet(authentication.getAuthorities());

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("authorities", authorities);

        List<String> urlPatterns = this.jdbcTemplate.query(this.sqlGetPrivilegesByAuthority, parameters, new MyAccessDecisionManager.AuthoritiesRowMapper());

        for (String urlPattern: urlPatterns) {

            AntPathRequestMatcher matcher = new AntPathRequestMatcher(urlPattern);

            if (matcher.matches(((FilterInvocation) o).getRequest()))
                return;

        }

        throw new AccessDeniedException("访问权限不足");

    }

    @Override
    public boolean supports(ConfigAttribute configAttribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }

    private final class AuthoritiesRowMapper implements RowMapper<String> {
        @Override
        public String mapRow(ResultSet rs, int rowNum) throws SQLException {
            return rs.getString("meta");
        }
    }
}
