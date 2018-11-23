package shop.service.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.dtos.product.BookDto;
import shop.dtos.product.ProductDto;
import shop.mappers.product.BookMapper;
import shop.model.product.Book;
import shop.repository.product.BookRepository;
import shop.repository.product.CategoryRepository;
import shop.service.message.Messages;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookService {

    private CategoryRepository categoryRepository;
    private BookRepository productRepository;
    private BookMapper mapper;
    private Messages messages;

    @Autowired
    public void setMessages(Messages messages) {
        this.messages = messages;
    }

    @Autowired
    public void setMapper(BookMapper mapper) {
        this.mapper = mapper;
    }

    @Autowired
    public void setCategoryRepository(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Autowired
    public void setProductRepository(BookRepository productRepository) {
        this.productRepository = productRepository;
    }


    private Book getById(Integer id) throws Exception {
        Book product = productRepository.findById(id).orElse(null);
        if (product == null) {
            throw new Exception(messages.get("error.notFound.book"));
        }
        return product;
    }

    public void delete(Integer id) {
        productRepository.deleteById(id);
    }

//    @Transactional(readOnly = true)
//    public List<ProductDto> getAll() {
//        List<Book> list = productRepository.findAll();
//        List<ProductDto> dtoList = new ArrayList<>();
//        for (Book product : list) {
//            ProductDto dto = mapper.getDto(product);
//            dtoList.add(dto);
//        }
//        return dtoList;
//    }

    // TODO: IMPLEMENT
    @Transactional(readOnly = true)
    public List<BookDto> getNewBooks() {

        // TODO: REDO
        List<BookDto> dtoList = new ArrayList<>();
        productRepository.findAll().forEach(book -> {
            BookDto dto = mapper.getIndexDto(book);
            dtoList.add(dto);
        });

        // TODO: REMOVE
        List<BookDto> list = new ArrayList<>();
        int i = 0;
        for (BookDto dto : dtoList) {
            list.add(dto);
            if (i > 1) break;
            i++;
        }

        return list;
    }


    // TODO: IMPLEMENT
    @Transactional(readOnly = true)
    public List<BookDto> getBestsellers() {

        // TODO: REDO
        List<BookDto> dtoList = new ArrayList<>();
        productRepository.findAll().forEach(book -> {
            BookDto dto = mapper.getIndexDto(book);
            dtoList.add(dto);
        });

        return dtoList;
    }

    @Transactional(readOnly = true)
    public ProductDto getDtoById(Integer id) throws Exception {
        Book product = getById(id);
        ProductDto orderDto = mapper.getDto(product);
        return orderDto;
    }

    public Integer create(ProductDto productDto) {
        Book product = mapper.getEntity(productDto);
        productRepository.save(product);
        return product.getId();
    }

    public void update(ProductDto dto) throws Exception {
        Book product = getById(dto.getProductId());
        Book updProduct = mapper.getEntity(dto);
        product = mapper.merge(product, updProduct);
        productRepository.save(product);
    }

}
