function parseFormacao(formacao) {
  if (formacao === "4-4-2") return { GOLEIRO: 1, LATERAL: 2, ZAGUEIRO: 2, MEIO_CAMPISTA: 4, ATACANTE: 2, TOTAL: 11 };
  if (formacao === "4-3-3") return { GOLEIRO: 1, LATERAL: 2, ZAGUEIRO: 2, MEIO_CAMPISTA: 3, ATACANTE: 3, TOTAL: 11 };
  if (formacao === "4-5-1") return { GOLEIRO: 1, LATERAL: 2, ZAGUEIRO: 2, MEIO_CAMPISTA: 5, ATACANTE: 1, TOTAL: 11 };
  return { GOLEIRO: 1, LATERAL: 2, ZAGUEIRO: 2, MEIO_CAMPISTA: 4, ATACANTE: 2, TOTAL: 11 };
}

function getPlayerInfo(inputEl) {
  const row = inputEl.closest(".player-row");
  const nameEl = row ? row.querySelector("span") : null;
  const imgEl = row ? row.querySelector("img") : null;
  return {
    id: inputEl.value,
    posicao: inputEl.getAttribute("data-posicao"),
    nome: nameEl ? nameEl.textContent.trim() : "",
    foto: imgEl ? imgEl.getAttribute("src") : null,
  };
}

function countSelectedByPosicao() {
  const checked = document.querySelectorAll('input[name="jogadorIds"]:checked');
  const counts = { GOLEIRO: 0, LATERAL: 0, ZAGUEIRO: 0, MEIO_CAMPISTA: 0, ATACANTE: 0, TOTAL: 0 };
  checked.forEach((el) => {
    const pos = el.getAttribute("data-posicao");
    if (counts[pos] !== undefined) counts[pos] += 1;
    counts.TOTAL += 1;
  });
  return counts;
}

function renderLine(containerId, items, totalSlots, label) {
  const container = document.getElementById(containerId);
  if (!container) return;
  container.innerHTML = "";

  const sorted = items.slice().sort((a, b) => a.nome.localeCompare(b.nome));

  for (let i = 0; i < totalSlots; i++) {
    const p = sorted[i];
    const div = document.createElement("div");
    div.className = "field-slot" + (p ? "" : " empty");

    if (p && p.foto) {
      const img = document.createElement("img");
      img.src = p.foto;
      img.alt = "";
      div.appendChild(img);
    } else {
      const spacer = document.createElement("div");
      spacer.style.width = "28px";
      spacer.style.height = "28px";
      div.appendChild(spacer);
    }

    const name = document.createElement("div");
    name.className = "name";
    name.textContent = p ? p.nome : "Vaga";
    div.appendChild(name);

    const role = document.createElement("div");
    role.className = "role pill";
    role.textContent = label;
    div.appendChild(role);

    container.appendChild(div);
  }
}

function updateUI() {
  const formacaoSelect = document.querySelector('select[name="formacao"]');
  const formacao = formacaoSelect ? formacaoSelect.value : "4-4-2";
  const limits = parseFormacao(formacao);
  const counts = countSelectedByPosicao();

  const totalEl = document.getElementById("count-total");
  if (totalEl) totalEl.textContent = counts.TOTAL + "/" + limits.TOTAL;

  const map = ["GOLEIRO", "LATERAL", "ZAGUEIRO", "MEIO_CAMPISTA", "ATACANTE"];
  map.forEach((p) => {
    const el = document.getElementById("count-" + p);
    if (el) el.textContent = counts[p] + "/" + limits[p];
  });

  // render campo (linhas)
  const checked = Array.from(document.querySelectorAll('input[name="jogadorIds"]:checked')).map(getPlayerInfo);
  const goleiros = checked.filter((p) => p.posicao === "GOLEIRO");
  const laterais = checked.filter((p) => p.posicao === "LATERAL");
  const zagueiros = checked.filter((p) => p.posicao === "ZAGUEIRO");
  const meios = checked.filter((p) => p.posicao === "MEIO_CAMPISTA");
  const atacantes = checked.filter((p) => p.posicao === "ATACANTE");

  renderLine("line-GOLEIRO", goleiros, limits.GOLEIRO, "GOL");
  renderLine("line-DEFESA", laterais.concat(zagueiros), limits.LATERAL + limits.ZAGUEIRO, "DEF");
  renderLine("line-MEIO", meios, limits.MEIO_CAMPISTA, "MEI");
  renderLine("line-ATAQUE", atacantes, limits.ATACANTE, "ATA");

  // trava seleção por posição e total
  const all = document.querySelectorAll('input[name="jogadorIds"]');
  all.forEach((el) => {
    const pos = el.getAttribute("data-posicao");
    const checked = el.checked;
    const reachedPos = counts[pos] >= limits[pos];
    const reachedTotal = counts.TOTAL >= limits.TOTAL;
    if (!checked && (reachedPos || reachedTotal)) {
      el.disabled = true;
    } else {
      el.disabled = false;
    }
  });
}

document.addEventListener("DOMContentLoaded", () => {
  document.addEventListener("change", (e) => {
    if (e.target && (e.target.matches('input[name="jogadorIds"]') || e.target.matches('select[name="formacao"]'))) {
      updateUI();
    }
  });

  const form = document.querySelector('form[method="post"]');
  if (form) {
    form.addEventListener("submit", () => {
      document.querySelectorAll('input[name="jogadorIds"]').forEach((el) => {
        el.disabled = false;
      });
    });
  }

  updateUI();
});

