package shop.controller.product;


import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import shop.model.product.Category;
import shop.repository.product.CategoryRepository;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping("${paths.categories}")
public class CategoryController {


    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryController(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @GetMapping("/all")
    @ApiOperation(value = "Retrieve all categories")
    public List<Category> retrieveAll() {
        return categoryRepository.findAll();
    }

    @GetMapping("/{id}")
    public Resource<Category> retrieveCategory(@PathVariable Integer id) {
        Optional<Category> category = categoryRepository.findById(id);
        Resource<Category> resource = new Resource<Category>(category.get());
        ControllerLinkBuilder linkTo = linkTo(methodOn(this.getClass()).retrieveAll());
        resource.add(linkTo.withRel("all"));
        return resource;
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        categoryRepository.deleteById(id);
    }

    @PostMapping
    public ResponseEntity<Object> create(@RequestBody Category category) {
        Category savedCategory = categoryRepository.save(category);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(savedCategory.getCategoryId()).toUri();

        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> update(@RequestBody Category category, @PathVariable Integer id) {

        Optional<Category> categoryOptional = categoryRepository.findById(id);

        if (!categoryOptional.isPresent())
            return ResponseEntity.notFound().build();

        category.setCategoryId(id);

        categoryRepository.save(category);

        return ResponseEntity.noContent().build();
    }
}