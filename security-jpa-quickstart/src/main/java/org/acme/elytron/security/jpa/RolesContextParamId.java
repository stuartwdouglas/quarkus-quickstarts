package org.acme.elytron.security.jpa;

import javax.enterprise.util.Nonbinding;
import javax.interceptor.InterceptorBinding;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@InterceptorBinding
public @interface RolesContextParamId {

    @Nonbinding
    String value();
}
