package com.poly.assimentshop.controller.admin;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.poly.assimentshop.domain.Category;
import com.poly.assimentshop.domain.Product;
import com.poly.assimentshop.model.CategoryDto;
import com.poly.assimentshop.model.ProductDto;
import com.poly.assimentshop.service.CategoryService;
import com.poly.assimentshop.service.ProductService;
import com.poly.assimentshop.service.StorageService;



@Controller
@RequestMapping("admin/products")
public class ProductController {
	@Autowired
	CategoryService categoryService;
	@Autowired
	ProductService productService;
	@Autowired
	StorageService storageService;
@ModelAttribute("categories")
public List<CategoryDto> getCategories(){
	return categoryService.findAll().stream().map(item -> {
		CategoryDto dto = new CategoryDto();
		BeanUtils.copyProperties(item, dto);
	}).toList();
}
	
@GetMapping("add")
public String add(Model model) {
	ProductDto dto = new ProductDto();
	dto.setIsEdit(false);
	model.addAttribute("product", dto);
	return "admin/products/addOrEdit";
}
@GetMapping("edit/{productId}")
public ModelAndView edit(ModelMap model, @PathVariable("productId") Long productId) {
	Optional<Product> opt = productService.findById(productId);
	ProductDto dto = new ProductDto();
	if(opt.isPresent()) {
		Product entity = opt.get();
		BeanUtils.copyProperties(entity, dto);
		dto.setCategoryId(entity.getCategory().getCategoryId());
		dto.setIsEdit(true);
	
		
		model.addAttribute("product", dto);
		return new ModelAndView("admin/products/addOrEdit", model);
	}
	model.addAttribute("message", "Not existed");
	return new ModelAndView("forward:/admin/products", model);
}
//@GetMapping("/images/{filename:.+}")
//@ResponseBody
//public ResponseEntity<Resource> serveFile(@PathVariable String filename){
//	Resource file = storageService.loadAsResource(filename);
//	return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"").body(file);
//}


@GetMapping("delete/{productId}")
public ModelAndView delete(ModelMap model,  @PathVariable("productId") Long productId) throws IOException {
	
	Optional<Product> opt = productService.findById(productId);
	if (opt.isPresent()) {
		if (!StringUtils.isEmpty(opt.get().getImage())) {
			storageService.delete(opt.get().getImage());
		}
		productService.delete(opt.get());
	
	model.addAttribute("message", "Product is deleted");
	}else {
		model.addAttribute("message", "Not deleted");
	}
	return new ModelAndView("forward:/admin/products", model);
} 
@PostMapping("saveOrUpdate")
public ModelAndView saveOrUpdate(ModelMap model, 
		@Valid @ModelAttribute("product") ProductDto dto, BindingResult result) {
	if (result.hasErrors()) {
		return new ModelAndView("admin/products/addOrEdit");
	}
	
	Product entity = new Product();
	BeanUtils.copyProperties(dto, entity);
	
	Category category = new Category();
	category.setCategoryId(dto.getCategoryId());
	entity.setCategory(category);
	
	
	productService.save(entity);
	model.addAttribute("message", "Product is saved!");
	return new ModelAndView("forward:/admin/products", model);
}
@RequestMapping("")
public String list(ModelMap model) {
	List<Product> list = productService.findAll();
	model.addAttribute("products", list);
	
	return "admin/products/list";
}
@GetMapping("search") //searchpaginated
public String search(ModelMap model,
 @RequestParam(name = "name", required = false) String name) {
//	@RequestParam("page") Optional<Integer> page,
//	@RequestParam("page") Optional<Integer> size) {
//	
//	int currentPage = page.orElse(1);
//	int pageSize = size.orElse(5);
//	
//	Pageable pageable = PageRequest.of(currentPage, pageSize, Sort.by("name"));
//	Page<Category> resultPage = null;
	List<Category> list = null;
	if(StringUtils.hasText(name)) {
		list = categoryService.findByNameContaining(name); // , pageable)
//		model.addAttribute("name", name);
	}else {
		list = categoryService.findAll();
	}
//	int totalPages = resultPage.getTotalPages();
//	if(totalPages >0) {
//		int start = Math.max(1, currentPage-2);
//		int end = Math.min(currentPage + 2, totalPages);
//		if(totalPages > 5) {
//			if(end == totalPages) {
//				if(end == totalPages) start = end - 5;
//				else if (start == 1) end = start + 5;
//			}
//			List<Integer> pageNumbers = IntStream.rangeClosed(start, end)
//					.boxed()
//					.collect(Collectors.toList());
//			model.addAttribute("pageNumbers", pageNumbers);
//		}
//	}
	
	model.addAttribute("products", list);
	
	return "admin/products/search";
}
}
