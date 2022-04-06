package ru.wartis.soa2.util;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ServiceDiscoveryConfiguration {

    @JsonProperty("ID")
    private String ID;

    @JsonProperty("Service")
    private String Service;

    @JsonProperty("Address")
    private String Address;

    @JsonProperty("Port")
    private int Port;

    public String getAddress() {
        return Address.equals("128.0.0.1") ? "localhost" : Address;
    }
}
