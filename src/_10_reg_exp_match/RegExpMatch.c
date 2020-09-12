#include <assert.h>
#include <stdbool.h>
#include <stdio.h>
#include <string.h>

bool isMatch(char *, char *);
bool isMatch2(char *, char *);
bool isMatchDp2(char *, char *);

int main() {
    assert(false == isMatch("aa", "a"));
    assert(true == isMatch("aa", "a*"));
    assert(true == isMatch("ab", ".*"));
    assert(true == isMatch("aab", "c*a*b"));
    assert(false == isMatch("mississippi", "mis*is*p*."));
    assert(true == isMatch("aaa", "a*a"));
    assert(false == isMatch("a", ".*..a*"));
    assert(true == isMatch("", "c*c*"));

    assert(false == isMatch2("aa", "a"));
    assert(true == isMatch2("aa", "a*"));
    assert(true == isMatch2("ab", ".*"));
    assert(true == isMatch2("aab", "c*a*b"));
    assert(false == isMatch2("mississippi", "mis*is*p*."));
    assert(true == isMatch2("aaa", "a*a"));
    assert(false == isMatch2("a", ".*..a*"));
    assert(true == isMatch2("", "c*c*"));

    assert(false == isMatchDp2("aa", "a"));
    assert(true == isMatchDp2("aa", "a*"));
    assert(true == isMatchDp2("ab", ".*"));
    assert(true == isMatchDp2("aab", "c*a*b"));
    assert(false == isMatchDp2("mississippi", "mis*is*p*."));
    assert(true == isMatchDp2("aaa", "a*a"));
    assert(false == isMatchDp2("a", ".*..a*"));
    assert(true == isMatchDp2("", "c*c*"));
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

bool isMatchDp2(char *s, char *p) {
    size_t slen = strlen(s);
    size_t plen = strlen(p);
    bool dp[slen + 1][plen + 1];
    for (size_t i = 0; i <= slen; i++) {
        for (size_t j = 0; j <= plen; j++) {
            dp[i][j] = false;
        }
    }
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

bool isMatchDp(char *s, char *p) {
    
}