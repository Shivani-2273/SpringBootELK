package com.elastic.springbootelastic.service;

import com.elastic.springbootelastic.entity.Customer;
import org.apache.lucene.search.join.ScoreMode;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class QueryDSLService {
    @Autowired
     ElasticsearchRestTemplate elasticsearchTemplate;
    public SearchHits<Customer> getCustomers(String fname,String lname) {
        Query searchQuery = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.boolQuery()
                        .must(QueryBuilders.matchQuery("fname",fname))
                        .should(QueryBuilders.matchQuery("lname",lname)))
                .build();

        SearchHits<Customer> articles =
                elasticsearchTemplate.search(searchQuery, Customer.class, IndexCoordinates.of("customer"));
                return articles;
    }

    public List<Customer> getDetails(String anything) {
        Map<String,Float> fields1 = new HashMap<String,Float>();
        fields1.put("fname",1f);
        fields1.put("lname",2f);

        Map<String,Float> fields2 = new HashMap<String,Float>();
        fields2.put("address.state",3f);
        fields2.put("address.city",4f);
        fields2.put("address.area",5f);

        Query searchQuery = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.queryStringQuery(anything+"*").fields(fields1))
                .build();

        Query searchQuery2 = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.nestedQuery("address",QueryBuilders.queryStringQuery(anything+"*").fields(fields2),ScoreMode.Avg))
                .build();

        SearchHits<Customer> articles =
                elasticsearchTemplate.search(searchQuery, Customer.class, IndexCoordinates.of("customer"));
        if(articles.isEmpty())
        {
            SearchHits<Customer> ans2 =
                    elasticsearchTemplate.search(searchQuery2, Customer.class, IndexCoordinates.of("customer"));
            return ans2.get().map(SearchHit::getContent).collect(Collectors.toList());
        }
        return articles.get().map(SearchHit::getContent).collect(Collectors.toList());
    }

    public List<Customer> getDetailswithTerm(String exact) {
        Query searchQuery1 = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.termQuery("fname",exact))
                .withQuery(QueryBuilders.termQuery("lname",exact))
                .build();

        SearchHits<Customer> articles =
                elasticsearchTemplate.search(searchQuery1, Customer.class, IndexCoordinates.of("customer"));

        System.out.println(articles.get().map(SearchHit::getContent).collect(Collectors.toList()));
        return articles.get().map(SearchHit::getContent).collect(Collectors.toList());
    }


    //Alternatives of wildcard with multimach query
//    public List<Customer> getDetailsWithQueryString(String data) {
//        Map<String,Float> fields = new HashMap<String,Float>();
//        fields.put("fname",1f);
//        fields.put("lname",2f);
//        Query searchQuery3 = new NativeSearchQueryBuilder()
//                .withQuery(QueryBuilders.queryStringQuery(data+"*").fields(fields))
//                .build();
//
//        SearchHits<Customer> articles =
//                elasticsearchTemplate.search(searchQuery3, Customer.class, IndexCoordinates.of("customer"));
//
//        System.out.println(articles.get().map(SearchHit::getContent).collect(Collectors.toList()));
//        return articles.get().map(SearchHit::getContent).collect(Collectors.toList());
//    }

//EXtra Queries practice
//            Query searchQuery = new NativeSearchQueryBuilder()
//                .withQuery(QueryBuilders.boolQuery()
//                                .should(QueryBuilders.queryStringQuery(anything+"*").fields(fields1))
//                        .must(QueryBuilders.nestedQuery("address",QueryBuilders.queryStringQuery(anything+"*").fields(fields2),ScoreMode.Avg)))
//      .withQuery(QueryBuilders.nestedQuery("address",QueryBuilders.queryStringQuery(anything+"*").fields(fields),ScoreMode.Avg))
//    .withQuery(QueryBuilders.wildcardQuery("fname",anything+"*"))
//                .withQuery(QueryBuilders.wildcardQuery("lname",anything+"*"))
//      .withQuery(QueryBuilders.nestedQuery("address", QueryBuilders.wildcardQuery("address.state",anything+"*"), ScoreMode.Avg))
//     .withQuery(QueryBuilders.wildcardQuery("city",anything+"*"))
//       .build();

}
