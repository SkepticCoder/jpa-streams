package ru.test.jpastreams.endpoint;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;
import ru.test.jpastreams.domain.FooBarEntity;
import ru.test.jpastreams.service.FooBarService;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Stream;

@RestController
@RequestMapping("/foobars")
public class FooBarController {

    private static final FooBarEntity EMPTY_FOO_BAR = new FooBarEntity();

    @Autowired
    private FooBarService fooBarService;

    @Autowired
    private ObjectMapper objectMapper;

    @GetMapping
    public List<FooBarEntity> getFooBars(Pageable pageable) {
        return fooBarService.getFooBarsList(pageable);
    }

    @GetMapping(value = "/links", produces = {MediaTypes.HAL_JSON_VALUE})
    public PagedResources<Resource<FooBarEntity>> getFooBars(Pageable pageable,
                                                             PagedResourcesAssembler<FooBarEntity> resourcesAssembler) {
        return resourcesAssembler.toResource(fooBarService.getFooBars(pageable));

    }

    @GetMapping("/{id}")
    public FooBarEntity getFoorBarById(@PathVariable Long id) {
        return fooBarService.getById(id).orElse(EMPTY_FOO_BAR);
    }

    @GetMapping(value = "/report", produces = {MediaType.APPLICATION_STREAM_JSON_VALUE})
    @Transactional(readOnly = true)
    public void getReport(OutputStream outputStream) {
        try (Stream<FooBarEntity> stream = fooBarService.streamAll()) {
            generateReport(stream, outputStream);
        }

    }

    @GetMapping(value = "/reportAsync", produces = {MediaType.APPLICATION_STREAM_JSON_VALUE})
    public StreamingResponseBody getReportAsync() {
        return s -> fooBarService.stream(
                stream -> generateReport(stream, s)
        );
    }

    private void generateReport(Stream<FooBarEntity> stream, OutputStream outputStream) {
        stream.map(this::serialize).forEach(writeToOS(outputStream));
    }

    private static Consumer<byte[]> writeToOS(OutputStream os) {
        return b -> {
            if (b == null || b.length == 0) {
                return;
            }

            try {
                os.write(b);
                os.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        };
    }

    private byte[] serialize(FooBarEntity value) {
        try {
            return objectMapper.writeValueAsBytes(value);
        } catch (JsonProcessingException e) {
            return new byte[0];
        }
    }

}
