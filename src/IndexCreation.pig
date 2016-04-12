collection = LOAD 'wikipedia_mapreduce.txt' AS (text: chararray);
collection_with_ids = RANK collection;

tokenized = FOREACH collection_with_ids GENERATE $0 AS doc_id, TOKENIZE(text) AS tokens;
tokens = FOREACH tokenized GENERATE doc_id, FLATTEN(tokens) AS token;
tokens_lower = FOREACH tokens GENERATE doc_id, LOWER(token) AS token;
tokens_distinct = DISTINCT tokens_lower;

tokens_grouped = FOREACH (GROUP tokens_distinct BY token) {
    doc_ids = FOREACH tokens_distinct GENERATE doc_id;
    doc_ids_sorted = ORDER doc_ids BY doc_id;
    GENERATE group AS token, doc_ids_sorted;
}

tokens_sorted = ORDER tokens_grouped BY token;
STORE tokens_sorted INTO 'results/pig/wikipedia_mapreduce_index';