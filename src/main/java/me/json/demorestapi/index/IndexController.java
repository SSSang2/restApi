package me.json.demorestapi.index;

import lombok.var;
import me.json.demorestapi.events.EventController;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@RestController
public class IndexController {

    @GetMapping("/api")
    public ResourceSupport index(){
        var index = new ResourceSupport();
        index.add(linkTo(EventController.class).withRel("events"));
        return index;

    }
}
