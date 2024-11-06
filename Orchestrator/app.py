from flask import Flask, request, jsonify
from langchain.vectorstores.chroma import Chroma
from langchain.prompts import ChatPromptTemplate
from langchain_community.llms.ollama import Ollama

from get_embedding_function import get_embedding_function
from populate_database import clear_database, load_documents, split_documents, add_to_chroma

app = Flask(__name__)
CHROMA_PATH = "chroma"

PROMPT_TEMPLATE = """
Answer the question based only on the following context:

{context}

---

Answer the question based on the above context: {question}
"""

class RAGSystem:
    db: any
    context_text: str
    model: any

    def __init__(self):
        self.populate_database()
        embedding_function = get_embedding_function()
        self.db = Chroma(persist_directory=CHROMA_PATH, embedding_function=embedding_function)
        self.model = Ollama(model="mistral")


    def populate_database(self):
        documents = load_documents()
        chunks = split_documents(documents)
        add_to_chroma(chunks)

    def query_rag(self, query_text: str):
        results = self.db.similarity_search_with_score(query_text, k=5)

        prompt_template = ChatPromptTemplate.from_template(PROMPT_TEMPLATE)
        self.context_text = "\n\n---\n\n".join([doc.page_content for doc, _score in results])

        prompt = prompt_template.format(context=self.context_text, question=query_text)

        response_text = self.model.invoke(prompt)

        formatted_response = f"Response: {response_text}"
        print(formatted_response)
        return response_text


rag_system = RAGSystem()


@app.route('/chat', methods=['POST'])
def chat():
    try:
        data = request.json
        if 'message' not in data:
            return jsonify({'error': 'No message provided'}), 400

        response = rag_system.query_rag(data['message'])

        return jsonify({
            'response': response
        })

    except Exception as e:
        return jsonify({'error': str(e)}), 500


@app.route('/health', methods=['GET'])
def health_check():
    return jsonify({'status': 'healthy'})


if __name__ == '__main__':
    app.run(host='0.0.0.0', port=5001)
