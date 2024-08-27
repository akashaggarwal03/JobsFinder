package Queries;

/**
 * Graphql Queries.
 */
public class GraphqlQueries {

    /**
     * To find top interview Experiences.
     */
    public static final String CATEGORY_TOPIC_LIST_QUERY = "query categoryTopicList($categories: [String!]!, $first: Int!, $orderBy: TopicSortingOption, $skip: Int, $query: String, $tags: [String!]) {\n" +
            "  categoryTopicList(categories: $categories, orderBy: $orderBy, skip: $skip, query: $query, first: $first, tags: $tags) {\n" +
            "    edges {\n" +
            "      node {\n" +
            "        id\n" +
            "        title\n" +
            "        post {\n" +               // Access the post object
            "          creationDate\n" +      // Include creationDate from post
            "        }\n" +
            "      }\n" +
            "    }\n" +
            "  }\n" +
            "}";


}
