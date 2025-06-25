package models;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class TokenResponse {
    private String scope;
    private String access_token;
    private String token_type;
    private Integer expires_in;
}
