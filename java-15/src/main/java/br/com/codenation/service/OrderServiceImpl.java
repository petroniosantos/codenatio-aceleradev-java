package br.com.codenation.service;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import br.com.codenation.model.OrderItem;
import br.com.codenation.model.Product;
import br.com.codenation.repository.ProductRepository;
import br.com.codenation.repository.ProductRepositoryImpl;

public class OrderServiceImpl implements OrderService {

	private static final float DISCOUNT_FOR_SALE = 0.2F;

	private ProductRepository productRepository = new ProductRepositoryImpl();

	/**
	 * Calculate the sum of all OrderItems
	 */
	@Override
	public Double calculateOrderValue(List<OrderItem> items) {
		return items.stream()
				.map(this::calculateItemValue)
				.reduce(0D, Double::sum);
	}

	/**
	 * Map from idProduct List to Product Set
	 */
	@Override
	public Set<Product> findProductsById(List<Long> ids) {
		return productRepository.findAll().stream()
				.filter(product -> ids.contains(product.getId()))
				.collect(Collectors.toCollection(LinkedHashSet::new));
	}

	/**
	 * Calculate the sum of all Orders(List<OrderIten>)
	 */
	@Override
	public Double calculateMultipleOrders(List<List<OrderItem>> orders) {
		return orders.stream()
				.map(this::calculateOrderValue)
				.reduce(0D, Double::sum);
	}

	/**
	 * Group products using isSale attribute as the map key
	 */
	@Override
	public Map<Boolean, List<Product>> groupProductsBySale(List<Long> productIds) {
		return findProductsById(productIds).stream()
				.collect(Collectors.groupingBy(Product::getIsSale));
	}

	private Double calculateItemValue(OrderItem item) {
		Product product = productRepository
				.findById(item.getProductId())
				.orElse(new Product(0L, "Null Product", 0D, false));

		float markup = product.getIsSale() ? 1 - DISCOUNT_FOR_SALE : 1;

		Double value = item.getQuantity() * product.getValue() * markup;

		return round(value, 2);
	};

	private double round(double value, int places) {
		long factor = (long) Math.pow(10, places);
		value = Math.round(value * factor);
		return value / factor;
	}

}