package olegood.rgx.mapper;

import java.util.Collection;
import java.util.function.Function;
import org.springframework.data.domain.Page;

/**
 * Represents a paginated response with content and pagination details. This class is intended to
 * encapsulate a page of content alongside associated paging metadata. It provides an abstraction to
 * facilitate the conversion of paginated data while allowing content transformation.
 *
 * @param <T> the type of elements in the content collection
 */
public record APage<T>(Collection<T> content, APageable pageable) {

  /**
   * Converts a {@link Page} of entities to a {@link APage} of a different type by applying a
   * mapping function to each entity in the content collection.
   *
   * @param <E> the type of elements in the source page
   * @param <T> the type of elements in the resulting {@link APage}
   * @param page the source {@link Page} containing the original entities
   * @param mapper a {@link Function} to map elements of type {@code E} to type {@code T}
   * @return an {@link APage} containing the transformed content and pagination details
   */
  public static <E, T> APage<T> from(Page<E> page, Function<E, T> mapper) {
    var content = page.getContent().stream().map(mapper).toList();
    return new APage<>(content, APageable.from(page));
  }

  /**
   * Represents pagination metadata for a page of content. This record encapsulates details about
   * the current page, total number of elements, and total number of pages, providing an abstraction
   * for handling pagination data.
   */
  private record APageable(int pageNumber, int pageSize, long totalElements, long totalPages) {

    /**
     * Creates an instance of {@code APageable} from the given {@link Page}.
     *
     * @param <T> the type of elements in the {@link Page}
     * @param page the source {@link Page} containing the pagination and content details
     * @return an {@code APageable} instance representing the pagination metadata extracted from the
     *     given {@link Page}
     */
    private static <T> APageable from(Page<T> page) {
      var p = page.getPageable();
      return new APageable(
          p.getPageNumber(), p.getPageSize(), page.getTotalElements(), page.getTotalPages());
    }
  }
}
