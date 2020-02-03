package de.cegeka.jax.frontend.v1.server.impl;

import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import de.cegeka.jax.backend.v1.api.DefaultApiClient;
import de.cegeka.jax.frontend.v1.api.EchoApiDelegate;
import de.cegeka.jax.frontend.v1.model.EchoRequest;
import de.cegeka.jax.frontend.v1.model.EchoResponse;
import feign.FeignException;

@Service
public class EchoForwarder implements EchoApiDelegate {

	private final static Logger logger = LoggerFactory.getLogger(EchoForwarder.class);

    private final DefaultApiClient client;

    @Autowired
    public EchoForwarder(DefaultApiClient client) {
        this.client = client;
    }

    @Override
    public ResponseEntity<EchoResponse> echoPost(EchoRequest echoRequest) {
        try {
			return convert(client.echoPost(convertRequest(echoRequest)), this::convert);
		} catch (FeignException e) {
			logger.warn("Failed to call the backend service: " + e.getMessage());
			return returnStatus(e);
		}
    }

    private de.cegeka.jax.backend.v1.model.EchoRequest convertRequest(EchoRequest echoRequest) {
        return new de.cegeka.jax.backend.v1.model.EchoRequest().sound(echoRequest.getSound());
    }

    private EchoResponse convert(de.cegeka.jax.backend.v1.model.EchoResponse body) {
		return new EchoResponse().echo(body.getEcho());
	}

    private <I, O> ResponseEntity<O> convert(ResponseEntity<I> responseEntity, Function<I, O> converter) {
		return new ResponseEntity<>(converter.apply(responseEntity.getBody()), responseEntity.getStatusCode());
	}

    private <T> ResponseEntity<T> returnStatus(FeignException e) {
		return returnStatus(e.status());
	}

	private <T> ResponseEntity<T> returnStatus(int status) {
		try {
			return new ResponseEntity<>(HttpStatus.valueOf(status));
		} catch (IllegalArgumentException e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
