package ch.igl.compta.repository.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;

import ch.igl.compta.CustomProperties;
import ch.igl.compta.model.ComptaPlan;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class ComptaProxy {

    @Autowired
    private CustomProperties props;

    public Iterable<ComptaPlan> getPlansActif() {
        String baseApiUrl = props.getApiTest();
        String getComptaPlanUrl = baseApiUrl + "/compta/plan?open=true";

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Iterable<ComptaPlan>> response = restTemplate.exchange(
            getComptaPlanUrl, 
            HttpMethod.GET,
            null,
            new ParameterizedTypeReference<Iterable<ComptaPlan>>() {});
        log.debug("get plans actives call " + response.getStatusCode().toString());

        return response.getBody();
    }

    public ComptaPlan getLastplanActif() {
        String baseApiUrl = props.getApiTest();
        String getComptaPlanUrl = baseApiUrl + "/compta/plan/actif";

        RestTemplate restTemplate = new RestTemplate();
        ObjectMapper mapper = new ObjectMapper();
        mapper.findAndRegisterModules();
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setObjectMapper(mapper);
        restTemplate.getMessageConverters().add(0, converter);
        /*ObjectMapper mapper = new ObjectMapper();
        mapper.getFactory().configure(JsonGenerator.Feature.IGNORE_UNKNOWN, true);
        mapper.activateDefaultTyping(mapper.getPolymorphicTypeValidator(), ObjectMapper.DefaultTyping.NON_FINAL);
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setObjectMapper(mapper);
        restTemplate.getMessageConverters().add(converter);*/
        ResponseEntity<ComptaPlan> response = restTemplate.exchange(
            getComptaPlanUrl, 
            HttpMethod.GET,
            null,
            ComptaPlan.class);
        log.debug("get last plan actif call " + response.getStatusCode().toString());

        return response.getBody();
    }

    public List<Pair<Long, String>> getPlansInfo() {
        String baseApiUrl = props.getApiTest();
        String getComptaPlanUrl = baseApiUrl + "/compta/plan"; // ?open=true

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<List<ComptaPlan>> response = restTemplate.exchange(
            getComptaPlanUrl, 
            HttpMethod.GET,
            null,
            new ParameterizedTypeReference<List<ComptaPlan>>() {});
        log.debug("get personnes call " + response.getStatusCode().toString());

        List<ComptaPlan> plans = response.getBody();
        if(plans == null) {
            plans = new ArrayList<>();
        }

        List<Pair<Long, String>> infos = plans.stream()
                                                .map(p -> Pair.of(p.getId(), p.getName()))
                                                .toList();

        return infos;
    }

    public ComptaPlan getPlanById(final long id) {
        String baseApiUrl = props.getApiTest();
        String getComptaPlanUrl = baseApiUrl + "/compta/plan/" + id;

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<ComptaPlan> response = restTemplate.exchange(
            getComptaPlanUrl, 
            HttpMethod.GET,
            null,
            ComptaPlan.class);
        log.debug("get plan by id call " + response.getStatusCode().toString());

        return response.getBody();
    }

    public ComptaPlan createPlan(ComptaPlan p) {
        String baseApiUrl = props.getApiTest();
        String getComptaPlanUrl = baseApiUrl + "/compta/plan";

        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<ComptaPlan> request = new HttpEntity<>(p);
        ResponseEntity<ComptaPlan> response = restTemplate.exchange(
            getComptaPlanUrl, 
            HttpMethod.POST,
            request,
            ComptaPlan.class);
        log.debug("create plan call " + response.getStatusCode().toString());

        return response.getBody();
    }

    public ComptaPlan modifyPlan(ComptaPlan p) {
        String baseApiUrl = props.getApiTest();
        String getComptaPlanUrl = baseApiUrl + "/compta/plan/" + p.getId();

        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<ComptaPlan> request = new HttpEntity<>(p);
        ResponseEntity<ComptaPlan> response = restTemplate.exchange(
            getComptaPlanUrl, 
            HttpMethod.PUT,
            request,
            ComptaPlan.class);
        log.debug("modify plan call " + response.getStatusCode().toString());

        return response.getBody();
    }

    public ComptaPlan deletePlan(final long id) {
        String baseApiUrl = props.getApiTest();
        String getComptaPlanUrl = baseApiUrl + "/compta/plan/" + id;

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<ComptaPlan> response = restTemplate.exchange(
            getComptaPlanUrl, 
            HttpMethod.DELETE,
            null,
            ComptaPlan.class);
        log.debug("delete plan call " + response.getStatusCode().toString());

        return response.getBody();
    }
}
