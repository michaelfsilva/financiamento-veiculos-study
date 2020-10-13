module.exports = {
    preset: 'jest-preset-angular',
    setupFilesAfterEnv: ['<rootDir>/src/setup-jest.ts'],
    roots: ['src'],
    moduleNameMapper: {
        "@services/(.*)": "<rootDir>/src/app/shared/services/$1",
        "@models/(.*)": "<rootDir>/src/app/shared/models/$1",
        "@images/(.*)": "<rootDir>/src/assets/images/$1",
        "@modules/(.*)": "<rootDir>/src/app/modules/$1",
        "@mocks/(.*)": "<rootDir>/src/assets/mocks/$1",
        "@shared/(.*)": "<rootDir>/src/app/shared/$1",
        "@env/(.*)": "<rootDir>/src/environments/$1"
      },
    transformIgnorePatterns: ['node_modules/(?!@ngrx)']
};