package team.asd.service;

import lombok.NonNull;
import org.apache.commons.collections4.CollectionUtils;
import team.asd.constants.ProductState;
import team.asd.entities.IsProduct;
import team.asd.exceptions.WrongProductException;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ProductService implements IsProductService {
	@Override
	public @NonNull List<String> defineProductNames(List<IsProduct> productList) throws WrongProductException {
		if (CollectionUtils.isEmpty(productList))
			return Collections.emptyList();

		for (IsProduct product : productList) {
			if (product == null || product.getName() == null)
				throw new WrongProductException("There is product which is null or without name");
		}

		return productList.stream()
				.map(IsProduct::getName)
				.collect(Collectors.toList());
	}

	@Override
	public List<IsProduct> defineProductsWithCreatedState(List<IsProduct> productList) {
		return CollectionUtils.isEmpty(productList) ?
				Collections.emptyList() :
				productList.stream()
						.filter(product -> isProductValid(product) && product.getState()
								.equals(ProductState.Created))
						.collect(Collectors.toList());
	}

	@Override
	public @NonNull Map<ProductState, Integer> calculateProductCountByState(List<IsProduct> productList) throws WrongProductException {
		Map<ProductState, Integer> resultMap = Arrays.stream(ProductState.values())
				.collect(Collectors.toMap(state -> state, state -> 0));

		if (CollectionUtils.isEmpty(productList))
			return resultMap;

		for (IsProduct product : productList) {
			if (!isProductValid(product)) {
				throw new WrongProductException("There is product which is null or without state");
			}
		}

		Map<ProductState, Integer> countedState = productList.stream()
				.collect(Collectors.groupingBy(IsProduct::getState, Collectors.reducing(0, e -> 1, Integer::sum)));

		resultMap.putAll(countedState);

		return resultMap;
	}

	@Override
	public @NonNull List<IsProduct> filterProductsByProvidedObject(List<IsProduct> productList, IsProduct product) throws WrongProductException {
		if (CollectionUtils.isEmpty(productList))
			return Collections.emptyList();

		if (product == null) {
			throw new WrongProductException("Provided object is null");
		}

		return productList.stream()
				.filter(p -> {
					if (p == null)
						return false;

					boolean matchesName = product.getName() == null || product.getName()
							.equals(p.getName());
					boolean matchesState = product.getState() == null || product.getState()
							.equals(p.getState());
					return matchesName && matchesState;
				})
				.collect(Collectors.toList());
	}

	private boolean isProductValid(IsProduct product) {
		return product != null && product.getState() != null;
	}
}
