package org.superbiz.moviefun.blobstore;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Objects;

public class ServiceCredentials {
    private final String vcapServices;

    public ServiceCredentials(String vcapServices) {
        this.vcapServices = vcapServices;
    }

    public String getCredentials(String name, String type, String credKey) {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode;

        try{
            jsonNode = objectMapper.readTree(vcapServices);

        } catch (IOException e) {
            throw new IllegalStateException("No VCAP_SERVICES found", e);
        }
        JsonNode services = jsonNode.path(type);
        for (JsonNode service : services) {
            if (Objects.equals(service.get("name").asText(), name)) {
                return service.get("credentials").get(credKey).asText();
            }
        }
        throw new IllegalStateException("No "+ name + " found in VCAP_SERVICES");
    }
}
