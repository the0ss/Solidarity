package theos.Solidarity.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.stream.Stream;

public enum Role {
    ROLE_USER("U"),
    ROLE_ADMIN("A");

    private String code;

    private Role(String code) {
        this.code = code;
    }
    @JsonCreator
    public static Role decode(final String code) {
        return Stream.of(Role.values()).filter(targetEnum -> targetEnum.code.equals(code)).findFirst().orElse(null);
    }

    @JsonValue
    public String getCode() {
        return code;
    }

}
