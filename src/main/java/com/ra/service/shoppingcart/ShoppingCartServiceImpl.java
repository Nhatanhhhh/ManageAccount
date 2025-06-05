package com.ra.service.shoppingcart;

import com.ra.model.dto.shoppingCartDTO.ShoppingCartRequestDTO;
import com.ra.model.dto.shoppingCartDTO.ShoppingCartResponseDTO;
import com.ra.model.entity.Product;
import com.ra.model.entity.ShoppingCart;
import com.ra.model.entity.User;
import com.ra.repository.ProductRepository;
import com.ra.repository.ShoppingCartRepository;
import com.ra.repository.UserRepository;
import com.ra.security.UserPrinciple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {
    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    private User getCurrentUser() {
        UserPrinciple userPrinciple = (UserPrinciple) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userRepository.findByEmail(userPrinciple.getUsername());
    }

    public List<ShoppingCartResponseDTO> getAllShoppingCarts() {
        User user = getCurrentUser();
        return shoppingCartRepository.findByUser(user).stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    public ShoppingCartResponseDTO addToCart(ShoppingCartRequestDTO requestDTO) {
        User user = getCurrentUser();
        Product product = productRepository.findById(requestDTO.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + requestDTO.getProductId()));
        if (!product.getStatus()) {
            throw new RuntimeException("Product is not available");
        }
        if (product.getStock() < requestDTO.getQuantity()) {
            throw new RuntimeException("Not enough stock available");
        }

        ShoppingCart existingCartItem = shoppingCartRepository.findByUserAndProductId(user, requestDTO.getProductId())
                .orElse(null);
        if (existingCartItem != null) {
            existingCartItem.setQuantity(existingCartItem.getQuantity() + requestDTO.getQuantity());
        } else {
            existingCartItem = new ShoppingCart();
            existingCartItem.setUser(user);
            existingCartItem.setProduct(product);
            existingCartItem.setQuantity(requestDTO.getQuantity());
        }
        ShoppingCart savedCartItem = shoppingCartRepository.save(existingCartItem);
        return convertToResponseDTO(savedCartItem);
    }

    public ShoppingCartResponseDTO updateCartQuantity(Long cartId, int quantity) {
        User user = getCurrentUser();
        ShoppingCart cartItem = shoppingCartRepository.findById(cartId)
                .orElseThrow(() -> new RuntimeException("Cart item not found with id: " + cartId));
        if (!cartItem.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Unauthorized access to cart item");
        }
        if (quantity <= 0) {
            shoppingCartRepository.delete(cartItem);
            return null;
        }
        if (cartItem.getProduct().getStock() < quantity) {
            throw new RuntimeException("Not enough stock available");
        }
        cartItem.setQuantity(quantity);
        ShoppingCart updatedCartItem = shoppingCartRepository.save(cartItem);
        return convertToResponseDTO(updatedCartItem);
    }

    public void deleteCartItem(Long cartId) {
        User user = getCurrentUser();
        ShoppingCart cartItem = shoppingCartRepository.findById(cartId)
                .orElseThrow(() -> new RuntimeException("Cart item not found with id: " + cartId));
        if (!cartItem.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Unauthorized access to cart item");
        }
        shoppingCartRepository.delete(cartItem);
    }

    // Xóa toàn bộ sản phẩm trong giỏ hàng
    public void clearCart() {
        User user = getCurrentUser();
        List<ShoppingCart> cartItems = shoppingCartRepository.findByUser(user);
        shoppingCartRepository.deleteAll(cartItems);
    }

    private ShoppingCartResponseDTO convertToResponseDTO(ShoppingCart cartItem) {
        return ShoppingCartResponseDTO.builder()
                .id(cartItem.getId())
                .productId(cartItem.getProduct().getId())
                .productName(cartItem.getProduct().getName())
                .quantity(cartItem.getQuantity())
                .unitPrice(cartItem.getProduct().getPrice())
                .build();
    }
}