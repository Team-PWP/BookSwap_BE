package team_pwp.swap_be.dto.common;

import jakarta.validation.constraints.Min;
import lombok.Builder;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@Builder
public record PagingRequest(@Min(0) int page, Integer size) {

    public PagingRequest {
        if (size == null) {
            size = 20;
        }
    }

    public Pageable toPageable() {
        return Pageable.ofSize(size).withPage(page);
    }

    public PageRequest toPageRequest() {
        return PageRequest.of(page, size);
    }
}