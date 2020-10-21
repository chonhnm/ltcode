#include <assert.h>
#include <stdbool.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

bool isMatch(char *, char *);
bool isMatch2(char *, char *);
bool isMatchDpBottomUp(char *, char *);
bool isMatchDpTopDown(char *, char *);
bool isMatchDpTopDownWithArr(char *, char *);

void test(bool(char *, char *));

void test(bool mtd(char *, char *)) {
    assert(false == mtd("aa", "a"));
    assert(true == mtd("aa", "a*"));
    assert(true == mtd("ab", ".*"));
    assert(true == mtd("aab", "c*a*b"));
    assert(false == mtd("mississippi", "mis*is*p*."));
    assert(true == mtd("aaa", "a*a"));
    assert(false == mtd("a", ".*..a*"));
    assert(true == mtd("", "c*c*"));
}

int main() {
    test(isMatch);
    test(isMatch2);
    test(isMatchDpBottomUp);
    test(isMatchDpTopDown);
    test(isMatchDpTopDownWithArr);
}

bool isMatch(char *s, char *p) {
    char *sp = s, *pp = p;
    char pc, pn, sc;
    bool isPeriod, isAsterisk;
    while (pc = *pp++) {
        pn = *pp;
        isPeriod = pc == '.';
        isAsterisk = pn == '*';
        if (isAsterisk) {
            pp++;
            while (sc = *sp) {
                if (isPeriod || pc == sc) {
                    if (isMatch(sp, pp)) {
                        return true;
                    } else {
                        sp++;
                    }
                } else {
                    break;
                }
            }
        } else {
            sc = *sp++;
            if (!sc || (!isPeriod && sc != pc)) {
                return false;
            }
        }
    }
    return !*sp;
}

// java solution translation
bool isMatch2(char *s, char *p) {
    if (strlen(p) == 0) return strlen(s) == 0;
    bool firstMatch = (strlen(s) != 0) && (*p == *s || *p == '.');
    if (strlen(p) >= 2 && *(p + 1) == '*') {
        return isMatch2(s, p + 2) || (firstMatch && isMatch2(s + 1, p));
    } else {
        return firstMatch && isMatch2(s + 1, p + 1);
    }
}

bool isMatchDpBottomUp(char *s, char *p) {
    size_t slen = strlen(s);
    size_t plen = strlen(p);
    bool dp[slen + 1][plen + 1];
    memset(dp, false, (slen + 1) * (plen + 1));
    dp[slen][plen] = true;

    for (int i = slen; i >= 0; i--) {
        for (int j = plen - 1; j >= 0; j--) {
            bool firstMatch = i < slen && (*(s + i) == *(p + j) || *(p + j) == '.');
            if (j + 1 < plen && *(p + j + 1) == '*') {
                dp[i][j] = dp[i][j + 2] || (firstMatch && dp[i + 1][j]);
            } else {
                dp[i][j] = firstMatch && dp[i + 1][j + 1];
            }
        }
    }
    return dp[0][0];
}

bool isMatchDpTopDown(char *s, char *p) {
    bool helper(int i, int j, char *s, char *p, char *[]);
    size_t slen = strlen(s);
    size_t plen = strlen(p);
    char *dp[slen + 1];
    for (size_t i = 0; i <= slen; i++) {
        dp[i] = calloc(sizeof *dp[i], plen + 1);
    }
    bool ans = helper(0, 0, s, p, dp);
    for (size_t i = 0; i <= slen; i++) {
        free(dp[i]);
    }
    return ans;
}

bool helper(int i, int j, char *s, char *p, char *dp[]) {
    if (dp[i][j] != '\0') {
        return dp[i][j] == '1';  // '1':true, '2':false
    }
    size_t plen = strlen(p);
    size_t slen = strlen(s);
    bool ans;
    if (j == plen) {
        ans = i == slen;
    } else {
        bool firstMatch = i < slen && (*(s + i) == *(p + j) || *(p + j) == '.');
        if (j + 1 < plen && *(p + j + 1) == '*') {
            ans = helper(i, j + 2, s, p, dp) ||
                  (firstMatch && helper(i + 1, j, s, p, dp));
        } else {
            ans = firstMatch && helper(i + 1, j + 1, s, p, dp);
        }
    }
    dp[i][j] = ans ? '1' : '2';
    return ans;
}

bool isMatchDpTopDownWithArr(char *s, char *p) {
    bool helperWithArr(int i, int j, char *s, char *p, char *);
    size_t slen = strlen(s);
    size_t plen = strlen(p);
    char dp[slen + 1][plen + 1];
    memset(dp, 0, (slen + 1) * (plen + 1));
    bool ans = helperWithArr(0, 0, s, p, (char *)dp);

    return ans;
}

bool helperWithArr(int i, int j, char *s, char *p, char *dp) {
    size_t plen = strlen(p);
    if (*(dp + i * (plen + 1) + j) != '\0') {
        return *(dp + i * (plen + 1) + j) == '1';  // '1':true, '2':false
    }
    size_t slen = strlen(s);
    bool ans;
    if (j == plen) {
        ans = i == slen;
    } else {
        bool firstMatch = i < slen && (*(s + i) == *(p + j) || *(p + j) == '.');
        if (j + 1 < plen && *(p + j + 1) == '*') {
            ans = helperWithArr(i, j + 2, s, p, (char *)dp) ||
                  (firstMatch && helperWithArr(i + 1, j, s, p, (char *)dp));
        } else {
            ans = firstMatch && helperWithArr(i + 1, j + 1, s, p, (char *)dp);
        }
    }
    *(dp + i * (plen + 1) + j) = ans ? '1' : '2';
    return ans;
}