import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { Button, Form } from 'react-bootstrap';

function QuestionList() {
    const [questions, setQuestions] = useState([]);
    const [currentQuestionIndex, setCurrentQuestionIndex] = useState(0);
    const [responses, setResponses] = useState({});
    const [isQuizComplete, setIsQuizComplete] = useState(false);
    const [showSubmitPage, setShowSubmitPage] = useState(false);
    const [score, setScore] = useState(null);
    const [showCreateQuizForm, setShowCreateQuizForm] = useState(false);
    const [quizForm, setQuizForm] = useState({ category: '', numQ: 1, title: '' });

    useEffect(() => {
        axios.get('http://localhost:8080/quiz/get/2')
            .then(response => {
                setQuestions(response.data);
            })
            .catch(error => {
                console.error('Error fetching data:', error);
            });
    }, []);

    const handleOptionClick = (questionId, option) => {
        setResponses(prevResponses => ({
            ...prevResponses,
            [questionId]: option
        }));
    };

    const handleSubmitQuestion = () => {
        if (currentQuestionIndex < questions.length - 1) {
            setCurrentQuestionIndex(currentQuestionIndex + 1);
        } else {
            // If it's the last question, show the "Submit Whole Quiz" page
            setShowSubmitPage(true);
        }
    };

    const handleSubmitQuiz = () => {
        const responseArray = Object.entries(responses).map(([questionId, response]) => ({
            id: parseInt(questionId, 10),
            response: response
        }));
        console.log(responseArray)
        axios.post('http://localhost:8080/quiz/submit/1', responseArray)
            .then(response => {
                console.log('Quiz results:', response.data);
                setScore(response.data);
                setIsQuizComplete(true); // Mark quiz as completed after submitting
            })
            .catch(error => {
                console.error('Error submitting quiz:', error);
                alert('An error occurred while submitting the quiz.');
            });
    };

    const handleCreateQuiz = () => {
        axios.post(`http://localhost:8080/quiz/create`, null, {
            params: {
                category: quizForm.category,
                numQ: quizForm.numQ,
                title: quizForm.title
            }
        })
        .then(response => {
            alert('New quiz created successfully!');
            setShowCreateQuizForm(false);
        })
        .catch(error => {
            console.error('Error creating quiz:', error);
            alert('An error occurred while creating the quiz.');
        });
    };

    if (isQuizComplete) {
        return (
            <div className="container mt-4 text-center">
                <h2>Your Score: {score !== null ? score : 'Calculating...'}</h2>
                <Button variant="primary" onClick={() => setShowCreateQuizForm(true)}>
                    Create a New Quiz
                </Button>

                {showCreateQuizForm && (
                    <Form className="mt-4">
                        <Form.Group controlId="category">
                            <Form.Label>Category</Form.Label>
                            <Form.Control
                                type="text"
                                value={quizForm.category}
                                onChange={(e) => setQuizForm({ ...quizForm, category: e.target.value })}
                                placeholder="Enter category"
                            />
                        </Form.Group>
                        <Form.Group controlId="numQ" className="mt-3">
                            <Form.Label>Number of Questions</Form.Label>
                            <Form.Control
                                type="number"
                                value={quizForm.numQ}
                                onChange={(e) => setQuizForm({ ...quizForm, numQ: parseInt(e.target.value, 10) })}
                                min="1"
                            />
                        </Form.Group>
                        <Form.Group controlId="title" className="mt-3">
                            <Form.Label>Title</Form.Label>
                            <Form.Control
                                type="text"
                                value={quizForm.title}
                                onChange={(e) => setQuizForm({ ...quizForm, title: e.target.value })}
                                placeholder="Enter title"
                            />
                        </Form.Group>
                        <Button variant="success" className="mt-3" onClick={handleCreateQuiz}>
                            Submit New Quiz
                        </Button>
                    </Form>
                )}
            </div>
        );
    }

    if (showSubmitPage) {
        return (
            <div className="container mt-4 text-center">
                <h2>You have completed all the questions!</h2>
                <Button variant="success" onClick={handleSubmitQuiz}>
                    Submit Whole Quiz
                </Button>
            </div>
        );
    }

    if (questions.length === 0) {
        return <div className="container mt-4 text-center">Loading questions...</div>;
    }

    const currentQuestion = questions[currentQuestionIndex];

    return (
        <div className="container mt-4 text-center">
            <h4>{currentQuestionIndex + 1}. {currentQuestion.questiontitle}</h4>
            <div className="d-flex flex-column align-items-center mt-3">
                {[currentQuestion.option1, currentQuestion.option2, currentQuestion.option3, currentQuestion.option4].map((option, optIndex) => (
                    <Button
                        key={optIndex}
                        variant={responses[currentQuestion.id] === option ? 'primary' : 'outline-primary'}
                        onClick={() => handleOptionClick(currentQuestion.id, option)}
                        className="mb-2"
                        style={{ width: '200px' }}
                    >
                        {option}
                    </Button>
                ))}
            </div>
            <div className="mt-3">
                <Button
                    variant="success"
                    onClick={handleSubmitQuestion}
                    disabled={!responses[currentQuestion.id]}
                >
                    Submit Question
                </Button>
            </div>
        </div>
    );
}

export default QuestionList;
