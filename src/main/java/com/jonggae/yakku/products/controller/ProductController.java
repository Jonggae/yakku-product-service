package com.jonggae.yakku.products.controller;

import com.jonggae.yakku.common.apiResponse.ApiResponseDto;
import com.jonggae.yakku.common.apiResponse.ApiResponseUtil;
import com.jonggae.yakku.common.messageUtil.MessageUtil;
import com.jonggae.yakku.products.dto.CustomPageDto;
import com.jonggae.yakku.products.dto.ProductDto;
import com.jonggae.yakku.products.messages.ProductApiMessages;
import com.jonggae.yakku.products.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    @GetMapping("/{productId}/order-info")
    public ResponseEntity<ProductDto> getProductOrderInfo(@PathVariable Long productId) {
        ProductDto productDto = productService.getProductInfo(productId);
        return ResponseEntity.ok(productDto);
    }

    @PostMapping()
    public ResponseEntity<ApiResponseDto<ProductDto>> addProduct(@RequestBody ProductDto productDto) {
        ProductDto addedProduct = productService.addProduct(productDto);
        String message = MessageUtil.getFormattedMessage(ProductApiMessages.PRODUCT_ADD_SUCCESS, productDto.getProductName());
        return ApiResponseUtil.success(message, addedProduct, 200);
    }

    //전체 상품 조회
    @GetMapping
    public ResponseEntity<ApiResponseDto<CustomPageDto<ProductDto>>> getProductList(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size) {
        CustomPageDto<ProductDto> products = productService.showAllProducts(page, size);
        String message = MessageUtil.getMessage(ProductApiMessages.PRODUCT_LIST_SUCCESS);
        return ApiResponseUtil.success(message, products, 200);
    }

    //단일 조회
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseDto<ProductDto>> getProduct(@PathVariable("id") Long id) {
        ProductDto product = productService.getProductInfo(id);
        String message = MessageUtil.getFormattedMessage(ProductApiMessages.PRODUCT_DETAIL_SUCCESS, product.getProductName());
        return ApiResponseUtil.success(message, product, 200);
    }

    // 상품 업데이트
    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponseDto<ProductDto>> updateProduct(@PathVariable("id") Long id, @RequestBody ProductDto productDto) {
        ProductDto updatedProduct = productService.updateProduct(id, productDto);
        String message = MessageUtil.getMessage(ProductApiMessages.PRODUCT_UPDATE_SUCCESS);
        return ApiResponseUtil.success(message, updatedProduct, 200);
    }

    // 상품 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseDto<CustomPageDto<ProductDto>>> deleteProduct(
            @PathVariable("id") Long id,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size) {
        CustomPageDto<ProductDto> afterDeletionProducts = productService.deleteProduct(id, page, size);
        String message = MessageUtil.getMessage(ProductApiMessages.PRODUCT_DELETE_SUCCESS);
        return ApiResponseUtil.success(message, afterDeletionProducts, 200);
    }
}
