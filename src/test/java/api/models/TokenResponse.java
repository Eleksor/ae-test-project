package api.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class TokenResponse {
    private String scope;
    private String access_token;
    private String token_type;
    private Integer expires_in;
    private Integer refresh_expires_in;
    private String refresh_token;
}
