package kitchenpos.ordertable.domain;

import javax.persistence.*;
import kitchenpos.tablegroup.domain.TableGroup;

@Entity
public class OrderTable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private TableGroup tableGroup;

    @Column(nullable = false)
    private int numberOfGuests;

    @Column(nullable = false)
    private boolean empty;

    protected OrderTable(){
    }

    public OrderTable(int numberOfGuests, boolean empty) {
        this.numberOfGuests = numberOfGuests;
        this.empty = empty;
    }

    public Long getId() {
        return id;
    }

    public TableGroup getTableGroup() {
        return tableGroup;
    }

    public int getNumberOfGuests() {
        return numberOfGuests;
    }

    public boolean isEmpty() {
        return empty;
    }

    public void bindTo(TableGroup tableGroup) {
        this.tableGroup = tableGroup;
        this.empty = false;
    }

    public void unbind() {
        tableGroup = null;
    }

    public boolean isGrouped() {
        return tableGroup != null;
    }

    public void changeEmpty(boolean empty) {
        if (isGrouped()) {
            throw new IllegalStateException("주문 테이블의 상태를 변경할 수 없습니다.");
        }
        this.empty = empty;
    }

    public void changeNumberOfGuests(int numberOfGuests) {
        if (numberOfGuests < 0) {
            throw new IllegalArgumentException("손님수는 음수일 수 없습니다.");
        }
        
        if (isEmpty()) {
            throw new IllegalStateException("인원수를 변경할 수 없습니다.");
        }
        this.numberOfGuests = numberOfGuests;
    }
}
