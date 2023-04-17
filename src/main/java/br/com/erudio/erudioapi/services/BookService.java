package br.com.erudio.erudioapi.services;

import br.com.erudio.erudioapi.controllers.BookController;
import br.com.erudio.erudioapi.data.vo.BookVO;
import br.com.erudio.erudioapi.exceptions.RequiredObjectIsNullException;
import br.com.erudio.erudioapi.exceptions.ResourceNotFoundException;
import br.com.erudio.erudioapi.mapper.DozerMapper;
import br.com.erudio.erudioapi.models.Book;
import br.com.erudio.erudioapi.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class BookService {
    private final Logger logger = Logger.getLogger(BookService.class.getName());
    @Autowired
    private BookRepository repository;

    public List<BookVO> findAll() {
        logger.info("Listing all books");
        List<BookVO> books = DozerMapper.parseListObjects(repository.findAll(), BookVO.class);
        books.forEach(book ->
                book.add(linkTo(methodOn(BookController.class).findById(book.getKey())).withSelfRel())
        );
        return books;
    }

    public BookVO findById(Long id) {
        logger.info("Finding one book!");
        Book entity = repository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
        BookVO vo = DozerMapper.parseObject(entity, BookVO.class);
        vo.add(linkTo(methodOn(BookController.class).findById(id)).withSelfRel());
        return vo;
    }

    public BookVO create(BookVO book) {
        logger.info("Creating one book!");
        if (book == null) throw new RequiredObjectIsNullException();
        Book entity = DozerMapper.parseObject(book, Book.class);
        BookVO vo =  DozerMapper.parseObject(repository.save(entity), BookVO.class);
        vo.add(linkTo(methodOn(BookController.class).findById(vo.getKey())).withSelfRel());
        return vo;
    }

    public BookVO update(BookVO book) {
        logger.info("Updating one book!");
        if (book == null) throw new RequiredObjectIsNullException();
        Book entity = DozerMapper.parseObject(this.findById(book.getKey()), Book.class);
        entity.setAuthor(book.getAuthor());
        entity.setLaunchDate(book.getLaunchDate());
        entity.setPrice(book.getPrice());
        entity.setTitle(book.getTitle());
        var vo =  DozerMapper.parseObject(repository.save(entity), BookVO.class);
        vo.add(linkTo(methodOn(BookController.class).findById(vo.getKey())).withSelfRel());
        return vo;
    }

    public void delete(Long id) {
        logger.info("Deleting one book");
        this.findById(id);
        repository.deleteById(id);
    }
}
