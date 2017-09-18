package id.cls.service;

import id.cls.domain.Product;
import id.cls.repository.ProductRepository;
import id.cls.repository.search.ProductSearchRepository;
import id.cls.service.dto.ProductDTO;
import id.cls.service.mapper.ProductMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

import static org.elasticsearch.index.query.QueryBuilders.*;
import java.util.UUID;

/**
 * Service Implementation for managing Product.
 * atiila consulting
 */

@Service
@Transactional
public class ProductService {

    private final Logger log = LoggerFactory.getLogger(ProductService.class);

    private final ProductRepository productRepository;

    private final ProductMapper productMapper;

    private final ProductSearchRepository productSearchRepository;

    public ProductService(ProductRepository productRepository, ProductMapper productMapper, ProductSearchRepository productSearchRepository) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
        this.productSearchRepository = productSearchRepository;
    }

    /**
     * Save a product.
     *
     * @param productDTO the entity to save
     * @return the persisted entity
     */
    public ProductDTO save(ProductDTO productDTO) {
        log.debug("Request to save Product : {}", productDTO);
        Product product = productMapper.toEntity(productDTO);
        product = productRepository.save(product);
        ProductDTO result = productMapper.toDto(product);
        productSearchRepository.save(product);
        return result;
    }

    /**
     *  Get all the products.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<ProductDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Products");
        return productRepository.findAll(pageable)
            .map(productMapper::toDto);
    }

    /**
     *  Get one product by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public ProductDTO findOne(UUID id) {
        log.debug("Request to get Product : {}", id);
        Product product = productRepository.findOneWithEagerRelationships(id);
        return productMapper.toDto(product);
    }

    /**
     *  Delete the  product by id.
     *
     *  @param id the id of the entity
     */
    public void delete(UUID id) {
        log.debug("Request to delete Product : {}", id);
        productRepository.delete(id);
        productSearchRepository.delete(id);
    }

    /**
     * Search for the product corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<ProductDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Products for query {}", query);
        Page<Product> result = productSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(productMapper::toDto);
    }

    public ProductDTO processExecuteData(Integer id, String param, ProductDTO dto) {
        ProductDTO r = dto;
        if (r != null) {
            switch (id) {
                case 1:
                    break;
                default:
                    break;
            }
		}
        return r;
    }

    public Set<ProductDTO> processExecuteListData(Integer id, String param, Set<ProductDTO> dto) {
        Set<ProductDTO> r = dto;
        if (r != null) {
            switch (id) {
                case 1:
                    break;
                default:
                    break;
            }
		}
        return r;
    }    

}
