package de.cegeka.jax.backend.v1.server.impl;

import de.cegeka.jax.backend.v1.api.EchoApiDelegate;
import de.cegeka.jax.backend.v1.model.EchoRequest;
import de.cegeka.jax.backend.v1.model.EchoResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class EchoGenerator implements EchoApiDelegate {

    @Override
    public ResponseEntity<EchoResponse> echoPost(EchoRequest echoRequest) {
        return new ResponseEntity<>(new EchoResponse().echo(echoRequest.getSound()), HttpStatus.OK);
    }

}
