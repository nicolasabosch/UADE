def fibonacci(n):
    anterior = 0
    actual = 1

    for i in range(n):
        auxiliar = anterior + actual
        anterior = actual
        actual = auxiliar

    return anterior


print(fibonacci(4))