package model;

import com.gmail.sshekh.model.RoleEnum;

import java.io.Serializable;

public class UserPrincipal implements Serializable {

    private Long id;
    private String email;
    private String name;
    private RoleEnum role;

    private UserPrincipal(Builder builder) {
        id = builder.id;
        email = builder.email;
        name = builder.name;
        role = builder.role;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public RoleEnum getRole() {
        return role;
    }

    public static final class Builder {

        private Long id;
        private String email;
        private String name;
        private RoleEnum role;

        private Builder() {}

        public Builder id(Long val) {
            id = val;
            return this;
        }

        public Builder email(String val) {
            email = val;
            return this;
        }

        public Builder name(String val) {
            name = val;
            return this;
        }

        public Builder role(RoleEnum val) {
            role = val;
            return this;
        }

        public UserPrincipal build() {
            return new UserPrincipal(this);
        }
    }
}
