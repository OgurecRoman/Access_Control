#include <iostream>
#include "Camera.h"
#include "Employee.h"
#include "Enterprise.h"

int main() {
    Camera camera(1, "192.168.1.1", 101);
    std::cout << camera.toString() << std::endl;

    Employee employee(1, "John Doe", 30, std::chrono::system_clock::now(), 101, "photo.jpg");
    std::cout << employee.toString() << std::endl;

    Enterprise enterprise(1, "Example Inc.");
    std::cout << enterprise.toString() << std::endl;

    return 0;
}