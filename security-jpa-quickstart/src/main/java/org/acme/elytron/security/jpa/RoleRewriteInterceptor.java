package org.acme.elytron.security.jpa;

import io.quarkus.security.identity.SecurityIdentity;
import io.quarkus.security.runtime.QuarkusSecurityIdentity;
import io.quarkus.security.runtime.SecurityIdentityAssociation;
import io.quarkus.vertx.http.runtime.CurrentVertxRequest;
import io.quarkus.vertx.http.runtime.security.QuarkusHttpUser;
import org.jboss.resteasy.core.ResteasyContext;

import javax.annotation.Priority;
import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.ws.rs.core.UriInfo;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

@Interceptor
@RolesContextParamId("")
@Priority(Interceptor.Priority.PLATFORM_BEFORE)
public class RoleRewriteInterceptor {

    private final Map<Method, String> roleIdByMethod = new ConcurrentHashMap<>();

    @Inject
    CurrentVertxRequest request;

    @Inject
    SecurityIdentityAssociation securityIdentityAssociation;

    @AroundInvoke
    public Object invoke(InvocationContext context) throws Exception {
        String param = roleIdByMethod.computeIfAbsent(context.getMethod(), new Function<Method, String>() {
            @Override
            public String apply(Method method) {
                RolesContextParamId an = method.getAnnotation(RolesContextParamId.class);
                if (an == null) {
                    Class<?> c = context.getTarget().getClass();
                    while (c != Object.class && an == null) {
                        an = c.getAnnotation(RolesContextParamId.class);
                        c = c.getSuperclass();
                    }
                }
                return an.value();
            }
        });
        UriInfo uri = ResteasyContext.getContextData(UriInfo.class);

        String actualOrgContextName = uri.getPathParameters().getFirst(param);
        SecurityIdentity newIdentity = QuarkusSecurityIdentity.builder()
                .setPrincipal(securityIdentityAssociation.getIdentity().getPrincipal())
                //setup roles based on current identity and actualOrgContextName
                .build();
        request.getCurrent().setUser(new QuarkusHttpUser(newIdentity));
        securityIdentityAssociation.setIdentity(newIdentity);
        return context.proceed();

    }
}
