package br.com.erudio.erudioapi.controllers;

import br.com.erudio.erudioapi.data.vo.BookVO;
import br.com.erudio.erudioapi.services.BookService;
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
@RequestMapping("/api/book/v1")
@Tag(name = "Books", description = "Endpoints for managing books")
public class BookController {
    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping(produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML})
    @Operation(
            summary = "Find all books",
            description = "This method returns all books",
            tags = {"Book"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "Success", content = {
                                    @Content(
                                            mediaType = MediaType.APPLICATION_JSON,
                                            array = @ArraySchema(schema = @Schema(implementation = BookVO.class))
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
    public List<BookVO> findAll() {
        return bookService.findAll();
    }

    @GetMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML})
    @Operation(
            summary = "Find book by id",
            description = "This method returns a book by id",
            tags = {"Book"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "Success", content = {
                                    @Content(schema = @Schema(implementation = BookVO.class)),
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
    public BookVO findById(@PathVariable("id") Long id) {
        return bookService.findById(id);
    }

    @PostMapping(
            produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML},
            consumes = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML}
    )
    @Operation(
            summary = "Create a new book",
            description = "This method creates a new book by passing in a JSON, XML or YAML representation of the book",
            tags = {"Book"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "Success", content = {
                                    @Content(schema = @Schema(implementation = BookVO.class)),
                                    @Content(mediaType = MediaType.APPLICATION_XML),
                                    @Content(mediaType = MediaType.APPLICATION_YML)
                            }),
                    @ApiResponse(responseCode = "400", description = "Bad request", content = @Content),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
                    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
            }
    )
    public BookVO create(@RequestBody BookVO book) {
        return bookService.create(book);
    }

    @PutMapping(
            produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML},
            consumes = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML}
    )
    @Operation(
            summary = "Update a book",
            description = "This method updates a book by passing in a JSON, XML or YAML representation of the book",
            tags = {"Book"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "Success", content = {
                                    @Content(schema = @Schema(implementation = BookVO.class)),
                                    @Content(mediaType = MediaType.APPLICATION_XML),
                                    @Content(mediaType = MediaType.APPLICATION_YML)
                            }),
                    @ApiResponse(responseCode = "400", description = "Bad request", content = @Content),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
                    @ApiResponse(responseCode = "404", description = "Not found", content = @Content),
                    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
            }
    )
    public BookVO update(@RequestBody BookVO book) {
        return bookService.update(book);
    }

    @DeleteMapping(value = "/{id}")
    @Operation(
            summary = "Delete a book",
            description = "This method deletes a book by passing in the book's id",
            tags = {"Book"},
            responses = {
                    @ApiResponse(responseCode = "204", description = "No content", content = @Content),
                    @ApiResponse(responseCode = "400", description = "Bad request", content = @Content),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
                    @ApiResponse(responseCode = "404", description = "Not found", content = @Content),
                    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
            }
    )
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        bookService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
