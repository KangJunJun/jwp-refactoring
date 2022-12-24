package kitchenpos.domain;

import java.math.BigDecimal;
import java.util.Objects;
import org.flywaydb.core.internal.util.StringUtils;

public class Product {
    private Long id;
    private String name;
    private BigDecimal price;

    public Product(){}

    public Product(String name, BigDecimal price) {
        validateName(name);
        validatePrice(price);
        this.name = name;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(final BigDecimal price) {
        this.price = price;
    }


    private void validateName(String name) {
        if (!StringUtils.hasText(name)) {
            throw new IllegalArgumentException("상품 이름은 필수값 입니다.");
        }
    }

    private void validatePrice(BigDecimal price) {
        if (Objects.isNull(price) || price.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("상품가격은 필수값이며, 0보다 커야합니다.");
        }
    }

}
