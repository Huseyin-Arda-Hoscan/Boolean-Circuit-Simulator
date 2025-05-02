# Boolean Circuit Simulator

[![Java Version](https://img.shields.io/badge/Java-11%2B-blue)](https://java.com)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

A Java implementation that converts Boolean expressions into 3-level logic circuits (NOT, AND, OR gates) and simulates their behavior.

## Features ✨
- Boolean expression parsing from text files
- Automatic 3-level circuit generation
- Interactive input simulation
- Support for complemented variables (A')
- Detailed circuit visualization

## Installation ⚙️
```bash
git clone https://github.com/yourusername/Boolean-Circuit-Simulator.git
cd Boolean-Circuit-Simulator
```

## Usage 🚀
1. Create `boole.txt`:
```text
F = A'B + AB' + BC
```
2. Run the simulator:
```bash
javac src/main/java/circuit/Main.java
java -cp src/main/java circuit.Main
```

## Example Output 📊
```
Circuit has 3 levels:
1. NOT Gates: A', C'
2. AND Gates: A'B, AB', BC
3. OR Gate: Final output

Enter values:
A=1, B=0, C=1 → Output: 1
```

## Contributing 🤝
Pull requests welcome! See [CONTRIBUTING.md](CONTRIBUTING.md) for guidelines.

## License 📄
MIT - See [LICENSE](LICENSE) for details
