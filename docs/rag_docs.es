PUT rag_docs
{
  "mappings": {
    "properties": {
      "content": {
        "type": "text"
      },
      "embedding": {
        "type": "dense_vector",
        "dims": 4096,
        "index": true,
        "similarity": "cosine"
      },
      "metadata": {
        "properties": {
          "source": { "type": "keyword" }
        }
      }
    }
  }
}
