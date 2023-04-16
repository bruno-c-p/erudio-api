package br.com.erudio.erudioapi.controllers;

import br.com.erudio.erudioapi.data.vo.PersonVO;
import br.com.erudio.erudioapi.services.PersonService;
import br.com.erudio.erudioapi.utils.MediaType;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/person/v1")
@Tag(name = "Persons", description = "Endpoints for managing persons")
public class PersonController {
    private final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping(produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML})
    @Operation(
            summary = "Find all persons",
            description = "This method returns all persons",
            tags = {"People"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "Success", content = {
                                    @Content(
                                            mediaType = MediaType.APPLICATION_JSON,
                                            array = @ArraySchema(schema = @Schema(implementation = PersonVO.class))
                                    ),
                                    @Content(mediaType = MediaType.APPLICATION_XML),
                                    @Content(mediaType = MediaType.APPLICATION_YML)
                            }),
                    @ApiResponse(responseCode = "400", description = "Bad request", content = @Content),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
                    @ApiResponse(responseCode = "404", description = "Not found", content = @Content),
                    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
            }
    )
    public List<PersonVO> findAll() {
        return personService.findAll();
    }

    @GetMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML})
    @Operation(
            summary = "Find person by id",
            description = "This method returns a person by id",
            tags = {"People"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "Success", content = {
                                    @Content(schema = @Schema(implementation = PersonVO.class)),
                                    @Content(mediaType = MediaType.APPLICATION_XML),
                                    @Content(mediaType = MediaType.APPLICATION_YML)
                            }),
                    @ApiResponse(responseCode = "204",description = "No content", content = @Content),
                    @ApiResponse(responseCode = "400", description = "Bad request", content = @Content),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
                    @ApiResponse(responseCode = "404", description = "Not found", content = @Content),
                    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
            }
    )
    public PersonVO findById(@PathVariable("id") Long id) {
        return personService.findById(id);
    }

    @PostMapping(
            produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML},
            consumes = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML}
    )
    @Operation(
            summary = "Create a new person",
            description = "This method creates a new person by passing in a JSON, XML or YAML representation of the person",
            tags = {"People"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "Success", content = {
                                    @Content(schema = @Schema(implementation = PersonVO.class)),
                                    @Content(mediaType = MediaType.APPLICATION_XML),
                                    @Content(mediaType = MediaType.APPLICATION_YML)
                            }),
                    @ApiResponse(responseCode = "400", description = "Bad request", content = @Content),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
                    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
            }
    )
    public PersonVO create(@RequestBody PersonVO person) {
        return personService.create(person);
    }

    @PutMapping(
            produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML},
            consumes = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML}
    )
    @Operation(
            summary = "Update a person",
            description = "This method updates a person by passing in a JSON, XML or YAML representation of the person",
            tags = {"People"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "Success", content = {
                                    @Content(schema = @Schema(implementation = PersonVO.class)),
                                    @Content(mediaType = MediaType.APPLICATION_XML),
                                    @Content(mediaType = MediaType.APPLICATION_YML)
                            }),
                    @ApiResponse(responseCode = "400", description = "Bad request", content = @Content),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
                    @ApiResponse(responseCode = "404", description = "Not found", content = @Content),
                    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
            }
    )
    public PersonVO update(@RequestBody PersonVO person) {
        return personService.update(person);
    }

    @DeleteMapping(value = "/{id}")
    @Operation(
            summary = "Delete a person",
            description = "This method deletes a person by passing in the person's id",
            tags = {"People"},
            responses = {
                    @ApiResponse(responseCode = "204", description = "No content", content = @Content),
                    @ApiResponse(responseCode = "400", description = "Bad request", content = @Content),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
                    @ApiResponse(responseCode = "404", description = "Not found", content = @Content),
                    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
            }
    )
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        personService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
