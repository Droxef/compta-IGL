package ch.igl.compta.repository.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import ch.igl.compta.CustomProperties;
import ch.igl.compta.model.Personne;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class PersonneProxy {

    @Autowired
    private CustomProperties props;

    public Iterable<Personne> getAllPersonnes() {
        String baseApiUrl = props.getApiTest();
        String getPersonneUrl = baseApiUrl + "/personnes";

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Iterable<Personne>> response = restTemplate.exchange(
            getPersonneUrl, 
            HttpMethod.GET,
            null,
            new ParameterizedTypeReference<Iterable<Personne>>() {});
        log.debug("get personnes call " + response.getStatusCode().toString());

        return response.getBody();
    }

    public Personne createPersonne(Personne p) {
        String baseApiUrl = props.getApiTest();
        String getPersonneUrl = baseApiUrl + "/personnes";

        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<Personne> request = new HttpEntity<>(p);
        ResponseEntity<Personne> response = restTemplate.exchange(
            getPersonneUrl, 
            HttpMethod.POST,
            request,
            Personne.class);
        log.debug("create personne call " + response.getStatusCode().toString());

        return response.getBody();
    }

    public Personne updatePersonne(Personne p) {
        String baseApiUrl = props.getApiTest();
        String getPersonneUrl = baseApiUrl + "/personnes/" + p.getId();

        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<Personne> request = new HttpEntity<>(p);
        ResponseEntity<Personne> response = restTemplate.exchange(
            getPersonneUrl, 
            HttpMethod.PUT,
            request,
            Personne.class);
        log.debug("modify personne call " + response.getStatusCode().toString());

        return response.getBody();
    }

    public Personne getPersonne(int id) {
        String baseApiUrl = props.getApiTest();
        String getPersonneUrl = baseApiUrl + "/personnes/" + id;

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Personne> response = restTemplate.exchange(
            getPersonneUrl, 
            HttpMethod.GET,
            null,
            Personne.class);
        log.debug("get personnes call " + response.getStatusCode().toString());

        return response.getBody();
    }

    public Personne deletePersonne(int id) {
        String baseApiUrl = props.getApiTest();
        String getPersonneUrl = baseApiUrl + "/personnes/" + id;

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Personne> response = restTemplate.exchange(
            getPersonneUrl, 
            HttpMethod.DELETE,
            null,
            Personne.class);
        log.debug("get personnes call " + response.getStatusCode().toString());

        return response.getBody();
    }

}
